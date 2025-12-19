pkg update -y && pkg upgrade -y
pkg install iproute2 -y
ip a
pkg install wireguard-tools dnsutils -y

pkg install netcat-openbsd -y
nc -vz 10.10.0.1 3389
