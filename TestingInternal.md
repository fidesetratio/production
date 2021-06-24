````
Tool busybox
kubectl run --generator=run-pod/v1 -i --tty busybox --image=radial/busyboxplus:curl --restart=Never -- sh


````

````
Example 

kube_node_status_condition{condition="Ready",job="kube-state-metrics",node="node-cosmos-06",status="unknown"} == 0


````

