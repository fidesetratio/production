
#note: use your own tag!
docker build . -t aimvector/fluentd-msig

#note: use your own tag!
docker push aimvector/fluentd-msig

kubectl create ns fluentd
kubectl apply -f 1-ks8/ -n fluentd


Jika menggunakan docker registry local ==> pastikan registry local sudah terinstall

#note: use your own tag!
docker build . -t logging/fluentd-msig

docker tag logging/fluentd-msig 128.21.24.30:5000/logging/fluentd-msig:latest


#note: use your own tag!
docker push localhost:5000/logging/fluentd-msig

# test 
docker pull 128.21.24.30:5000/logging/fluentd-msig:latest

kubectl create ns fluentd
kubectl apply -f 1-ks8/ -n fluentd


