#### Server IP

128.21.24.60

username : administrator

password : P@ssw0rd

### Command

````
sudo su -i
ufw disable

{
  apt install -y apt-transport-https ca-certificates curl gnupg-agent software-properties-common
  curl -fsSL https://download.docker.com/linux/ubuntu/gpg | apt-key add -
  add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
  apt update
  apt install -y docker-ce=5:19.03.10~3-0~ubuntu-focal containerd.io
}

docker info | grep -i cgroup


cat > /etc/docker/daemon.json <<EOF
{
"exec-opts": ["native.cgroupdriver=systemd"],
"log-driver": "json-file",
"log-opts": {
"max-size": "100m"
},
"storage-driver": "overlay2"
}
EOF


systemctl daemon-reload 
systemctl restart docker
systemctl enable docker

exit
sudo groupadd docker
sudo usermod -aG docker $USER
newgrp docker

execute gitlab.sh

http://128.21.24.60:8888/users/sign_in

username: root
password: P@ssw0rd


Please make everything acccessible
1. Backups
sudo chown administrator:administrator /home/administrator/envcosmos/gitlab/data
2. Secret and Json
cd /home/administrator/envcosmos/gitlab/config
sudo chmod 755 *.*






````



#### Reset Password



````
reset all password
docker exec -it gitlab gitlab-rails console -e production


user = User.find_by(username: "root")
user.password = 'P@ssw0rd'
user.password_confirmation = 'P@ssw0rd'
user.save


````



#### Backup from docker gitlab

````

docker exec -it gitlab gitlab-rake gitlab:backup:create STRATEGY=copy

docker exec gitlab gitlab-rake gitlab:backup:create STRATEGY=copy
docker exec gitlab ls /var/opt/gitlab/backups/
docker exec gitlab rm -rf /var/opt/gitlab/backups/*.tar


docker cp gitlab:/var/opt/gitlab/backups/ .





cp /home/administrator/envcosmos/gitlab-volume/data/backups/1623293526_2021_06_10_13.6.1_gitlab_backup.tar
/home/administrator/envcosmos/backup

docker cp /home/administrator/envcosmos/backup-gitlab/1623293526_2021_06_10_13.6.1_gitlab_backup.tar gitlab:/var/opt/gitlab/backups/



docker exec -it gitlab ls /var/opt/gitlab/backups/

docker exec -it gitlab chown git:git /var/opt/gitlab/backups/

docker exec -it gitlab chown git:git /var/opt/gitlab/backups/1623293526_2021_06_10_13.6.1_gitlab_backup.tar

docker exec -it gitlab gitlab-rake


docker exec -it gitlab gitlab-rake gitlab:backup:restore
docker cp gitlab-secrets.json gitlab:/etc/gitlab/gitlab-secrets.json
docker exec -it gitlab gitlab-ctl restart
docker exec -it gitlab gitlab-rake gitlab:check SANITIZE=true
reset

docker exec -it gitlab gitlab-rails console -e production












````



