#### Server IP

128.21.24.61

username : administrator

password : P@ssw0rd

### Command

````
sudo su -i
ufw disable
swapoff -a; sed -i '/swap/d' /etc/fstab

cat >>/etc/sysctl.d/kubernetes.conf<<EOF
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system


{
  apt install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
  add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  apt update
  apt install -y docker-ce=5:19.03.10~3-0~ubuntu-focal containerd.io
}

cat > /etc/docker/daemon.json <<EOF
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"storage-driver": "overlay2"
}
EOF

systemctl daemon-reload 
systemctl restart docker
systemctl enable docker


{
  curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
  echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" > /etc/apt/sources.list.d/kubernetes.list
}

apt update && apt install kubectl=1.18.5-00

exit
mkdir -p $HOME/.kube
scp administrator@128.21.24.50:/home/administrator/envcosmos/kubernetes-conf/admin.conf ~/.kube/config

sudo groupadd docker
sudo usermod -aG docker $USER
newgrp - docker


````



#### 

````

docker container run -d \
    -p 8081:8080 \
    -v /home/administrator/envcosmos/jenkins-volume3:/var/jenkins_home \
    --name jenkins \
    --restart always \
    jenkins/jenkins:lts
    
    

docker container run -d \
    -p 8081:8080 \
    -v /home/administrator/envcosmos/jenkins-volume3:/var/jenkins_home \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -v $(which docker):/usr/bin/docker \
    --name jenkins \
    --restart always \
    jenkins/jenkins:lts

sudo chmod 755 /var/run/docker.sock

    
docker exec jenkins cat var/jenkins_home/secrets/initialAdminPassword


install maven and java

masuk ke dalam level 0
docker exec -u 0 -it f7a05b7a0bb3 bash



install gettext
apt-get update
apt-get install -y gettext-base


apt-get install -y wget
wget --no-verbose -O /tmp/apache-maven-3.6.3-bin.tar.gz https://archive.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz 
tar xzf /tmp/apache-maven-3.6.3-bin.tar.gz -C /opt
ln -s /opt/apache-maven-3.6.3 /opt/maven

ln -s /opt/maven/bin/mvn /usr/local/bin

rm -rf /tmp/apache-maven-3.6.3-bin.tar.gz
export MAVEN_HOME=/opt/maven
chown -R jenkins:jenkins /opt/maven
chmod 666 /var/run/docker.sock




````



