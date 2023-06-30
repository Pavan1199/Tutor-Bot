import utils

sbcoupling_microservice_name = 'sbcoupling-ms'
VALUEAUC_PORT = '8084'
nav_to_sbcoupling_dir = utils.base_dir + '/BE/SBCoupling'
nav_to_sbcoupling_target_dir = nav_to_sbcoupling_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/sbcoupling'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_sbcoupling_target_dir
