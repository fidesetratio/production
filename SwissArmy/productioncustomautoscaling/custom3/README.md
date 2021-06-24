 ./gencerts.sh
kubectl create ns custom-metrics
kubectl apply -f cm-adapter-serving-certs.yaml -n custom-metrics
kubectl apply -f deploy/manifests/

kubectl get --raw /apis/custom.metrics.k8s.io/v1beta1







Please see this for demo

https://www.programmersought.com/article/83405497196/