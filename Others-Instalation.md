# Others Instalation
Follow this documentation to set up a Other instalation in master cluster on __Ubuntu 20.04 LTS__.

This documentation guides you in setting up a other instalation in master.

#### Metal LB

````
curl -s https://raw.githubusercontent.com/google/metallb/v0.9.3/manifests/namespace.yaml | less

kubectl apply -f https://raw.githubusercontent.com/google/metallb/v0.9.3/manifests/namespace.yaml

kubectl get ns

curl -s https://raw.githubusercontent.com/google/metallb/v0.9.3/manifests/metallb.yaml
 | less
 
 kubectl apply -f https://raw.githubusercontent.com/google/metallb/v0.9.3/manifests/metallb.yaml

sipcalc 128.21.0.0/16 // make sure calculate rage is there

pastikan pake segmen ini : 128.21.32.1 - 128.21.32.100

vim /tmp/metallb.yaml

apiVersion: v1
kind: ConfigMap
metadata:
  namespace: metallb-system
  name: config
data:
  config: |
    address-pools:
    - name: default
      protocol: layer2
      addresses:
      - 128.21.36.1 - 128.21.36.254
  
kubectl create -f /tmp/metallb.yaml

// Test
kubectl expose deploy nginx --port 80 --type LoadBalancer

//test2
kubectl create deploy nginx2 --image nginx
kubectl expose deploy nginx2 --port 80 --type LoadBalancer

kubectl create deploy nginx --image 128.21.24.60:8086/m
kubectl expose deploy nginx --port 80 --type LoadBalancer


add memberlist
kubectl create secret generic -n metallb-system memberlist --from-literal=secretkey="$(openssl rand -base64 128)"

btw to change metallb configuration

1. kubectl -n metallb-system delete cm config ==> delete the old configmap
2. kubectl apply -f /tmp/metallb.yaml ==> apply the new configmap
3. kubectl -n metallb-system delete pod --all ==> delete the metallb pods
4. kubectl -n metallb-system get pods -w ==> watch the pods come back up

Note :
Jika ada kesulitan mengenai docker hub limitasi retry, pastikan menggunakan 
nexus-local-repository dengan mengubah destination pull registry ke local
ex : metall.yaml



````







#### Helm & Nginx Ingress



````
curl -fsSL -o get_helm.sh https://raw.githubusercontent.com/helm/helm/master/scripts/get-helm-3

chmod 700 get_helm.sh

 ./get_helm.sh

helm version --short

helm repo list


helm repo add stable https://charts.helm.sh/stable --force-update
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update
helm search repo ingress
helm show values ingress-nginx/ingress-nginx > /tmp/ingress-nginx.yaml
vim /tmp/ingress-nginx.yaml
change 
- hostPort.enabled:true
- hostnetwork:True
- kind: DaemonSet
kubectl create ns ingress-nginx

helm install cosmosingress ingress-nginx/ingress-nginx -n ingress-nginx --values=/tmp/ingress-nginx.yaml

helm list -n ingress-nginx




````

