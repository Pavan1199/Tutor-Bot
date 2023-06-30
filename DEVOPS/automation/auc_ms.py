import utils

auc_microservice_name = 'auc-ms'
auc_PORT = '8081'
nav_to_auc_dir = utils.base_dir + '/BE/AUC'
nav_to_auc_target_dir = nav_to_auc_dir + '/target'
nav_to_DEVOPS_auc_dir = utils.base_dir + '/DEVOPS/auc'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_auc_dir + \
    '/Dockerfile ' + nav_to_auc_target_dir
