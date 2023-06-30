import utils

pbcoupling_microservice_name = 'pbcoupling-ms'
VALUEAUC_PORT = '8084'
nav_to_pbcoupling_dir = utils.base_dir + '/BE/PBCoupling'
nav_to_pbcoupling_target_dir = nav_to_pbcoupling_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/pbcoupling'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_pbcoupling_target_dir
