# Install Kubernetes Cluster using kubeadm
Follow this documentation to set up a Kubernetes cluster on __Ubuntu 20.04 LTS__.

This documentation guides you in setting up a cluster with one master node and one worker node.

## Assumptions
| Role   | FQDN       | IP            | OS           | RAM  | CPU  |
| ------ | ---------- | ------------- | ------------ | ---- | ---- |
| Master | master     | 172.16.16.100 | Ubuntu 20.04 | 2G   | 2    |
| Worker | workernode | 172.16.16.101 | Ubuntu 20.04 | 1G   | 1    |

## On both Kmaster and Kworker
##### Login as `root` user
```
sudo su -
```
Perform all the commands as root user unless otherwise specified

### Set Hostname and Add entries in /etc/hosts file

````
$ sudo hostnamectl set-hostname "master"     // Run this command on master node
$ sudo hostnamectl set-hostname "workernode"     // Run this command on workernode
````

Add the following entries in /etc/hosts files on each node,

```
172.16.16.100    master
172.16.16.101    node-0
```



##### Disable Firewall
```
ufw disable
```
##### Disable swap
```
swapoff -a; sed -i '/swap/d' /etc/fstab
or
swapoff -a
sudo vi /etc/fstab
remarks /swap.img 
or
sudo sed -i '/ swap / s/^\(.*\)$/#\1/g' /etc/fstab
sudo swapoff -a
```
##### Update sysctl settings for Kubernetes networking
```
cat >>/etc/sysctl.d/kubernetes.conf<<EOF
net.bridge.bridge-nf-call-ip6tables = 1
net.bridge.bridge-nf-call-iptables = 1
EOF
sysctl --system
```
##### Install docker engine
```
{
  apt install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
  add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  apt update
  apt install -y docker-ce=5:19.03.10~3-0~ubuntu-focal containerd.io
}
```
#### Change Group cgroup Driver

````bash
docker info | grep -i cgroup

change group driver if not systemd

cat > /etc/docker/daemon.json <<EOF
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"storage-driver": "overlay2",
"insecure-registries" : ["localhost:8086", "localhost:8087","128.21.24.60:8086", "128.21.24.60:8087"]

}
EOF

systemctl daemon-reload 
systemctl restart docker
systemctl enable docker
 
````



### Kubernetes Setup

##### Add Apt repository
```
{
  curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
  echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" > /etc/apt/sources.list.d/kubernetes.list
}
```
##### Install Kubernetes components
```
apt update && apt install -y kubeadm=1.18.5-00 kubelet=1.18.5-00 kubectl=1.18.5-00
```
##### In case you are using LXC containers for Kubernetes nodes
Hack required to provision K8s v1.15+ in LXC containers
```
{
  mknod /dev/kmsg c 1 11
  echo '#!/bin/sh -e' >> /etc/rc.local
  echo 'mknod /dev/kmsg c 1 11' >> /etc/rc.local
  chmod +x /etc/rc.local
}
```

## On Master
##### Initialize Kubernetes Cluster
Update the below command with the ip address of kmaster
```
kubeadm init --apiserver-advertise-address=172.16.16.100 --pod-network-cidr=192.168.0.0/16  --ignore-preflight-errors=all
```
##### Deploy Calico network
```
kubectl --kubeconfig=/etc/kubernetes/admin.conf create -f https://docs.projectcalico.org/v3.14/manifests/calico.yaml
```

##### Cluster join command
```
kubeadm token create --print-join-command
```

##### To be able to run kubectl commands as non-root user
If you want to be able to run kubectl commands as non-root user, then as a non-root user perform these
```
mkdir -p $HOME/.kube
sudo cp -i /etc/kubernetes/admin.conf $HOME/.kube/config
sudo chown $(id -u):$(id -g) $HOME/.kube/config
```

## On Node
##### Join the cluster
Use the output from __kubeadm token create__ command in previous step from the master server and run here.

## Verifying the cluster (On Master)
##### Get Nodes status
```
kubectl get nodes
```
##### Get component status
```
kubectl get cs
```

Have Fun!!

#### Example

```
kubectl create deployment nginx --image=nginx
kubectl create service nodeport nginx --tcp=80:80
kubectl scale deployments nginx --replicas=3
```

