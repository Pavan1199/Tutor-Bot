import utils

bdc_microservice_name = 'bdconsistency-ms'
BDC_PORT = '8081'
nav_to_bdc_dir = utils.base_dir + '/BE/BDConsistency'
nav_to_bdc_target_dir = nav_to_bdc_dir + '/target'
nav_to_DEVOPS_BDC_dir = utils.base_dir + '/DEVOPS/bdconsistency'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_BDC_dir + \
    '/Dockerfile ' + nav_to_bdc_target_dir
