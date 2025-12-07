package org.lovekapibarasan.shutdown_android.config;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.lovekapibarasan.shutdown_android.AppConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Element;

public class LockConfig {

    private final Context context;
    private final File configFile;

    private static final String CONFIG_FILE_NAME = "lock_config.xml";
    // Request Code(ID for activity)

    public LockConfig(Context context, String configPath) {
        Log.d(AppConstants.TAG, "LockManager: Constructor started");
        Log.d(AppConstants.TAG, "LockManager: configPath = " + configPath);
        this.context = context;
        this.configFile = new File(context.getFilesDir(), CONFIG_FILE_NAME);
    }

    public List<String> loadPackageList() {
        List<String> packages = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(this.configFile);

            NodeList packageNodes = doc.getElementsByTagName("package");
            for (int i = 0; i < packageNodes.getLength(); i++) {
                Node node = packageNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    packages.add(node.getTextContent());
                }
            }

        } catch (Exception e) {
            Log.e(AppConstants.TAG, "Error", e);
        }

        return packages;
    }
    public void importUserXml(Uri uri) {
        try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
             FileOutputStream outputStream = new FileOutputStream(configFile)) {

            if (inputStream == null) {
                Log.e(AppConstants.TAG, "Failed to open input stream");
                return;
            }

            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

            Log.d(AppConstants.TAG, "XML imported successfully");

        } catch (Exception e) {
            Log.e(AppConstants.TAG, "Error importing XML", e);
        }
    }

    public List<RepeatingTriggerConfig> getRepeatingTriggers() {
        List<RepeatingTriggerConfig> triggers = new ArrayList<>();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(this.configFile);

            NodeList triggerNodes = doc.getElementsByTagName("trigger");
            for (int i = 0; i < triggerNodes.getLength(); i++) {
                Node node = triggerNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String type = element.getAttribute("type");

                    if ("repeating".equals(type)) {
                        Integer startHour = getIntValueOrNull(element, "start_hour");
                        Integer startMinute = getIntValueOrNull(element, "start_minute");
                        Long intervalMinutes = getLongValueOrNull(element, "interval_minutes");
                        Integer endHour = getIntValueOrNull(element, "end_hour");
                        Integer endMinute = getIntValueOrNull(element, "end_minute");
                        Long durationMinutes = getLongValueOrNull(element, "duration_minutes");

                        triggers.add(new RepeatingTriggerConfig(
                                startHour, startMinute, intervalMinutes,
                                endHour, endMinute, durationMinutes
                        ));
                    }
                }
            }

        } catch (Exception e) {
            Log.e(AppConstants.TAG, "Error", e);
        }

        return triggers;
    }

    private Integer getIntValueOrNull(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return Integer.parseInt(nodes.item(0).getTextContent());
        }
        return null;
    }

    private Long getLongValueOrNull(Element element, String tagName) {
        NodeList nodes = element.getElementsByTagName(tagName);
        if (nodes.getLength() > 0) {
            return Long.parseLong(nodes.item(0).getTextContent());
        }
        return null;
    }

}