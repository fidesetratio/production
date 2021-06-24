kubectl exec -it traffic-generator sh
apk add --no-cache wrk

wrk -c 5 -t 5 -d 99 -H "Connectin: Close" http://application-cpu