import utils

valuebd_microservice_name = 'valuebd-ms'
WORKBD_PORT = '8084'
nav_to_valuebd_dir = utils.base_dir + '/BE/ValueBD'
nav_to_valuebd_target_dir = nav_to_valuebd_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/valuebd'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_valuebd_target_dir
