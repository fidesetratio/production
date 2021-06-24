kubectl apply -f metrix -n kubesystem
kubectl top pods
kubectl top nodes

kubectl scale deploy/application-cpu --replicas 1
kubectl autoscale deploy/application-cpu  --cpu-percent=95 --min=1 --max=10
kubectl get hpa/application-cpu -o wide

