import utils

workbd_microservice_name = 'workbd-ms'
WORKBD_PORT = '8084'
nav_to_workbd_dir = utils.base_dir + '/BE/WorkBD'
nav_to_workbd_target_dir = nav_to_workbd_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/workbd'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_workbd_target_dir
