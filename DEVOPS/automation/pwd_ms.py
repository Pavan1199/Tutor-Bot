import utils

pwd_microservice_name = 'pwd-ms'
DON_PORT = '8088'
nav_to_pwd_dir = utils.base_dir + '/BE/PWD'
nav_to_pwd_target_dir = nav_to_pwd_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/pwd'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_pwd_target_dir
