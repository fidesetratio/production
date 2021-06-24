#### Server IP

128.21.24.60

username : administrator

password : P@ssw0rd

### Command

````
-- after docker instalation ready

sudo nexus.sh
docker logs nexus -f

````



#### 

````

https://www.dimas-maryanto.com/notes/docker/nexus-docker-private-registry
https://developer.skao.int/en/latest/tools/containers/docker-proxy-cache.html
Step by step create hosted, pull domain hosts





Simple way to tagging
docker tag jettech/kube-webhook-certgen:v1.5.1 localhost:8087/jettech/kube-webhook-certgen:v1.5.1

docker push localhost:8087/jettech/kube-webhook-certgen:v1.5.1
docker push 128.21.24.60:8086/jettech/kube-webhook-certgen:v1.5.1




docker push 128.21.24.60:8087/nginx:latest


docker tag metallb/controller:v0.9.3 localhost:8087/metallb/controller:v0.9.3

docker tag jimmidyson/configmap-reload:v0.3.0 localhost:8087/jimmidyson/configmap-reload:v0.3.0
docker push localhost:8087/jimmidyson/configmap-reload:v0.3.0
docker pull 128.21.24.60:8086/jimmidyson/configmap-reload:v0.3.0

docker push localhost:8087/metallb/controller:v0.9.3
docker pull 128.21.24.60:8086/metallb/controller:v0.9.3


docker tag metallb/controller:v0.9.3 128.21.24.60:8087/metallb/controller:0.9.3
docker push 128.21.24.60:8087/metallb/controller:0.9.3

docker tag nginx:latest 128.21.24.60:8086/v2/nginx2:latest
docker tag nginx:latest 128.21.24.60:8087/v2/nginx2:latest
docker push 128.21.24.60:8086/v2/nginx2:latest
docker push 128.21.24.60:8087/v2/nginx2:latest









````



