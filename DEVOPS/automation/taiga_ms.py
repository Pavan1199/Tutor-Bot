import utils

taiga_microservice_name = 'taiga-ms'
TAIGA_PORT = '8080'
nav_to_taiga_dir = utils.base_dir + '/BE/taiga'
nav_to_taiga_target_dir = nav_to_taiga_dir + '/target'
nav_to_DEVOPS_TAIGA_dir = utils.base_dir + '/DEVOPS/taiga'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_TAIGA_dir + \
    '/Dockerfile ' + nav_to_taiga_target_dir
