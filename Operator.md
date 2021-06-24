# Prometheous Operator
````
1. kubectl create ns msigmonitoring
2. kubectl apply -f crds/ -n msigmonitoring
3. kubectl apply -f operator/ -n msigmonitoring
4. kubectl apply -f prometheous/ -n msigmonitoring
5. kubectl apply -f monitorservice/1-service-monitor-prometheous.yaml -n msigmonitoring
6. kubectl apply -f monitorservice/2-service-monitor-apiserver.yaml -n msigmonitoring
7. kubectl apply -f monitorservice/3-service-monitor-kubelet.yaml -n msigmonitoring
8. kubectl apply -f monitorservice/5-kube-state-metrics/ -n msigmonitoring
9. kubectl apply -f monitorservice/5-service-monitor-kube-state-metrics.yaml -n msigmonitoring
10. kubectl apply -f monitorservice/6-alert-manager/ -n msigmonitoring
11. kubectl apply -f monitorservice/6-service-monitor-alert-manager.yaml -n msigmonitoring
12. kubectl apply -f monitorservice/7-grafana/ -n msigmonitoring


 
````

