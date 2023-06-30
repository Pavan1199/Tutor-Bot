import utils

deliveryontime_microservice_name = 'deliveryontime-ms'
DON_PORT = '8088'
nav_to_deliveryontime_dir = utils.base_dir + '/BE/DeliveryOnTime'
nav_to_deliveryontime_target_dir = nav_to_deliveryontime_dir + '/target'
nav_to_DEVOPS_WORKBD_dir = utils.base_dir + '/DEVOPS/deliveryontime'
copy_Dockerfile_to_target = 'cp ' + nav_to_DEVOPS_WORKBD_dir + \
    '/Dockerfile ' + nav_to_deliveryontime_target_dir
