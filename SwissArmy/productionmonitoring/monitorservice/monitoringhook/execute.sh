docker build -t msig/monitoring-hook:0.0.2 .
docker tag msig/monitoring-hook:0.0.2 128.21.24.60:8087/msig/monitoring-hook:0.0.2 
docker push 128.21.24.60:8087/msig/monitoring-hook:0.0.2
docker pull 128.21.24.60:8086/msig/monitoring-hook:0.0.2

