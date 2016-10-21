# CMPE272 Project instruction

## Set up Jenkins on EC2 AWS
#### Reference link: [Jenkins Tutorial] (https://wiki.jenkins-ci.org/display/JENKINS/Installing+Jenkins+on+Ubuntu)
1. Reserve AWS EC2 instance
2. Follow the following instruction to install Jenkins
  * wget -q -O - https://pkg.jenkins.io/debian/jenkins-ci.org.key | sudo apt-key add -
  * sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'
  * sudo apt-get update
  * sudo apt-get install jenkins
3. Set up nginx proxy from 80->8080 (There are multiple ways to do routing in the tutorial)
  * sudo apt-get install nginx
  * cd /etc/nginx/sites-available
  * sudo rm default ../sites-enabled/default
  * Create configuration file for jenkins
   
    upstream app_server {
      
        server 127.0.0.1:8080 fail_timeout=0;
    
    }
    
    server {

        listen 80;
        
        listen [::]:80 default ipv6only=on;
        server_name ci.yourcompany.com;

        location / {
            proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
            proxy_set_header Host $http_host;
            proxy_redirect off;

            if (!-f $request_filename) {
                proxy_pass http://app_server;
                break;
            }
        }
     }
   
   * sudo ln -s /etc/nginx/sites-available/jenkins /etc/nginx/sites-enabled/
   * Restart jenkins and nginx  
        ** sudo service nginx restart   
        ** sudo service jenkins start
