# Set up a Highly Available Kubernetes Cluster using kubeadm
Follow this documentation to set up a highly available Kubernetes cluster using __Ubuntu 20.04 LTS__.

This documentation guides you in setting up a cluster with two master nodes, one worker node and a load balancer node using HAProxy.

Please consult your way to https://tansanrao.com/kubernetes-ha-cluster-with-kubeadm/

## Vagrant Environment
| Role          | FQDN                     | IP            | OS           | RAM  | CPU  |
| ------------- | ------------------------ | ------------- | ------------ | ---- | ---- |
| Load Balancer | loadbalancer.example.com | 172.16.16.100 | Ubuntu 20.04 | 1G   | 1    |
| Master        | kmaster1.example.com     | 172.16.16.101 | Ubuntu 20.04 | 2G   | 2    |
| Master        | kmaster2.example.com     | 172.16.16.102 | Ubuntu 20.04 | 2G   | 2    |
| Worker        | kworker1.example.com     | 172.16.16.201 | Ubuntu 20.04 | 1G   | 1    |

> * Password for the **root** account on all these virtual machines is **kubeadmin**
> * Perform all the commands as root user unless otherwise specified

## Pre-requisites
If you want to try this in a virtualized environment on your workstation
* Virtualbox installed
* Vagrant installed
* Host machine has atleast 8 cores
* Host machine has atleast 8G memory

## Bring up all the virtual machines
```
vagrant up
```

## Set up load balancer node
##### Install Haproxy
```
apt update && apt install -y haproxy
```
##### Configure haproxy
Append the below lines to /etc/haproxy/haproxy.cfg**
```
frontend kubernetes-frontend
    bind 172.16.16.100:6443
    mode tcp
    option tcplog
    default_backend kubernetes-backend

backend kubernetes-backend
    mode tcp
    option tcp-check
    balance roundrobin
    server kmaster1 172.16.16.101:6443 check fall 3 rise 2
    server kmaster2 172.16.16.102:6443 check fall 3 rise 2
```
##### Restart haproxy service
```
systemctl restart haproxy
```

## On all kubernetes nodes (kmaster1, kmaster2, kworker1)
##### Disable Firewall
```
ufw disable
```
##### Disable swap
```
swapoff -a; sed -i '/swap/d' /etc/fstab
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
  apt update && apt install -y docker-ce=5:19.03.10~3-0~ubuntu-focal containerd.io
}
```
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
apt update && apt install -y kubeadm=1.19.2-00 kubelet=1.19.2-00 kubectl=1.19.2-00
```
## On any one of the Kubernetes master node (Eg: kmaster1)
##### Initialize Kubernetes Cluster
```
kubeadm init --control-plane-endpoint="172.16.16.100:6443" --upload-certs --apiserver-advertise-address=172.16.16.101 --pod-network-cidr=192.168.0.0/16
```
Copy the commands to join other master nodes and worker nodes.
##### Deploy Calico network
```
kubectl --kubeconfig=/etc/kubernetes/admin.conf create -f https://docs.projectcalico.org/v3.15/manifests/calico.yaml
```

## Join other nodes to the cluster (kmaster2 & kworker1)
> Use the respective kubeadm join commands you copied from the output of kubeadm init command on the first master.

> IMPORTANT: You also need to pass --apiserver-advertise-address to the join command when you join the other master node.

## Downloading kube config to your local machine
On your host machine
```
mkdir ~/.kube
scp root@172.16.16.101:/etc/kubernetes/admin.conf ~/.kube/config
```
Password for root account is kubeadmin (if you used my Vagrant setup)

## Verifying the cluster
```
kubectl cluster-info
kubectl get nodes
kubectl get cs
```



### External Stacked etd

````
https://tansanrao.com/kubernetes-ha-cluster-with-kubeadm/
https://ystatit.medium.com/backup-and-restore-kubernetes-etcd-on-the-same-control-plane-node-20f4e78803cb
https://ystatit.medium.com/how-to-change-kubernetes-kube-apiserver-ip-address-402d6ddb8aa2
````





### Print Command

'

``` Print Command
// token first
kubeadm init phase upload-certs --upload-certs

//create print join command
kubeadm token create --certificate-key <generated certificate key> --print-join-command

// for new master
kubeadm join <loadbalancer:port> --token <token> --discovery-token-ca-cert-hash:sha256xxx
--control-plane --certificate-key <key> --apiserver-advertise-address <newmasterip>

// for new node
kubeadm join <loadbalancer:port> --token <token> --discovery-token-ca-cert-hash:sha256xxx


```

#### Step by Step Add new master

````
login to new master

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
  apt update && apt install -y docker-ce=5:19.03.10~3-0~ubuntu-focal containerd.io
}

{
  curl -s https://packages.cloud.google.com/apt/doc/apt-key.gpg | apt-key add -
  echo "deb https://apt.kubernetes.io/ kubernetes-xenial main" > /etc/apt/sources.list.d/kubernetes.list
}

apt update && apt install -y kubeadm=1.18.5-00 kubelet=1.18.5-00 kubectl=1.18.5-00


go to master-node-01

sudo kubeadm init phase upload-certs --upload-certs
sudo kubeadm token create --certificate-key ebb5b80dab799f780d00559ffe2228483265bd985997de1396097b58fd08cfb0 --print-join-command


kubeadm join 128.21.24.53:6443 --token bb8ozs.iqv8b7xkjnlnq0ml --discovery-token-ca-cert-hash sha256:0b5c8efc8b2d4d556397aa2bbe0c259620a0104a46558819523ac269e057e548  --control-plane --certificate-key ebb5b80dab799f780d00559ffe222848325bd985997de1396097b58fd08cfb0 --apiserver-advertise-address 128.21.24.52 



````

