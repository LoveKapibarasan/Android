### Unlock Bootloader
0. Device Information, About phone, tablet > developer options > enable developer options, build number(7 clicks)
1. Get unlock key from the maker.


1. Enable Developer Options (tap Build Number 7 times)
2. Developers Option
    * Enable OEM(Original Equipment Manufacturer) Unlocking
    * Enable USB Debugging
3. USB mode
    * File Transfer/MTP or (PTP)
    * ☓: USB tethering

**Enter FASTBOOT MODE**

```bash
# From computer:
adb reboot bootloader # Or Volume Up/Down + Power
# Check that device is in bootloader mode:
fastboot devices
# !! Erase all data on the device !!\
fastboot oem unlock
# or newer command
fastboot flashing unlock
```

> Sometimes Windows need Google USB driver
> [Reference](https://pashalog.com/google-usb-driver/)
- Integrated with ROM
- Secret Key is managed by maker

**Recovery Mode**
* Mount /System
* sideload

### TWRP(Team Win Recovery Project)
* Enhanced recovery mode

### How to Get Root Privileges on Android = Wie man Zugriff auf Wurzelrechte erhält
**`Magisk`**
* [URL](https://droidwin.com/root-lineageos-magisk-boot-img/)
* Some bank application might not work.
* From `boot.img`, generate patched(customized) `magisk_patched.img`
> need `boot.img`
      * Hook initramfs（ramdisk）
      * Process that works on RAM before mounting system.img or data.img
* OTA(Over-The-Air) version boot.img

**KernelSU**
* GKI 2.0(Android 12 ~): Generic Kernel Interface
* Boot.img has raw, gz, lz4 format

**DSU Loader**
* Install GSI via DSU.
* [Reference](https://note.com/kiphoneix/n/na9fcf8f523e2)


```bash
# Extract *.xz
xz -d ${name}.img.xz
# Install GSI Script
adb shell sh "/storage/emulated/0/Android/data/vegabobo.dsusideloader/files/install"

# DSU command
cmd dsu list
adb shell dsu start /sdcard/system.img

adb shell
su
dd if=/dev/block/mmcblk0p9 of=/sdcard/boot.img
exit
exit
adb pull /sdcard/boot.img C:\Users\lovek\Downloads\
```

### Fastboot

* Fastboot = Bootloader Protocol and Command-Line Tool = Protokoll und Befehlszeilentool für den
Bootloader



### Best smartphone maker for programmer

* ASUS
* Google Pixel
* PinePhone
### OS
* Ubuntu
* LineageOS
    * root addon

### Why LineageOS needs device-specific builds: = Warum benötigt LineageOS device-spezifische Builds:

* Different bootloaders - Each manufacturer uses their own proprietary bootloader with different unlock methods

* Different System-on-Chips (SoCs) -  each need specific drivers and configurations

* Custom hardware configurations - Each phone has unique:
    * Camera sensors and ISP (Image Signal Processor)
    * Display panels and touch controllers
    * Fingerprint sensors
    * GPS, WiFi, Bluetooth chips
    * Battery management

* Device Tree - describes the exact hardware layout for each specific phone model

**Android**
* Android Core
* Android API  should be the same.
