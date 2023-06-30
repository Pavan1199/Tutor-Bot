import os
import time
import springboot
import dockercommand
import taiga_ms
import bdconsistency_ms
import wordbd_ms
import valueauc_ms
import deliveryontime_ms
import pbcoupling_ms
import sbcoupling_ms
import pwd_ms
import auc_ms
import valuebd_ms
import utils


automationScriptDir = utils.base_dir + '/DEVOPS/automation'

# Docker stop all processes and remove all containers
print('Stopping all Previous Docker Containers....')
os.system(dockercommand.dockerStopAllProcesses())
time.sleep(1)
print('Removing all previous containers....')
os.system(dockercommand.dockerRemoveAllContainers())
time.sleep(1)
print('Creating New Docker Images....')


# Deploy Taiga Microservice
print('Creating Docker Image for TAIGA micro-service....')
os.chdir(taiga_ms.nav_to_taiga_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(taiga_ms.taiga_microservice_name))
os.system(taiga_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(taiga_ms.nav_to_taiga_target_dir)
os.system(dockercommand.dockerImageBuild(taiga_ms.taiga_microservice_name))
# os.system(dockercommand.dockerRunImage(
#     taiga_ms.taiga_microservice_name, taiga_ms.TAIGA_PORT, dockercommand.DOCKER_DEMON_MODE))

time.sleep(1)

# Deploy AUC Microservice
print('Creating Docker Image for AUC micro-service....')
os.chdir(auc_ms.nav_to_auc_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    auc_ms.auc_microservice_name))
os.system(auc_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(auc_ms.nav_to_auc_target_dir)
os.system(dockercommand.dockerImageBuild(
    auc_ms.auc_microservice_name))

# Deploy BD Consistency Microservice
print('Creating Docker Image for BD Consistency micro-service....')
os.chdir(bdconsistency_ms.nav_to_bdc_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    bdconsistency_ms.bdc_microservice_name))
os.system(bdconsistency_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(bdconsistency_ms.nav_to_bdc_target_dir)
os.system(dockercommand.dockerImageBuild(
    bdconsistency_ms.bdc_microservice_name))

# Deploy Delivery On Time Microservice
print('Creating Docker Image for Delivery On Time micro-service....')
os.chdir(deliveryontime_ms.nav_to_deliveryontime_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    deliveryontime_ms.deliveryontime_microservice_name))
os.system(deliveryontime_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(deliveryontime_ms.nav_to_deliveryontime_target_dir)
os.system(dockercommand.dockerImageBuild(
    deliveryontime_ms.deliveryontime_microservice_name))
os.system(springboot.run_spring_boot_start_application)


# Deploy PBCoupling Microservice
print('Creating Docker Image for PBCoupling micro-service....')
os.chdir(pbcoupling_ms.nav_to_pbcoupling_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    pbcoupling_ms.pbcoupling_microservice_name))
os.system(pbcoupling_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(pbcoupling_ms.nav_to_pbcoupling_target_dir)
os.system(dockercommand.dockerImageBuild(
    pbcoupling_ms.pbcoupling_microservice_name))


# Deploy PWD Microservice
print('Creating Docker Image for PWD micro-service....')
os.chdir(pwd_ms.nav_to_pwd_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    pwd_ms.pwd_microservice_name))
os.system(pwd_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(pwd_ms.nav_to_pwd_target_dir)
os.system(dockercommand.dockerImageBuild(
    pwd_ms.pwd_microservice_name))

# Deploy SBCoupling Microservice
print('Creating Docker Image for SBCoupling micro-service....')
os.chdir(sbcoupling_ms.nav_to_sbcoupling_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    sbcoupling_ms.sbcoupling_microservice_name))
os.system(sbcoupling_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(sbcoupling_ms.nav_to_sbcoupling_target_dir)
os.system(dockercommand.dockerImageBuild(
    sbcoupling_ms.sbcoupling_microservice_name))

# Deploy ValueAUC Microservice
print('Creating Docker Image for ValueAUC micro-service....')
os.chdir(valueauc_ms.nav_to_valueauc_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    valueauc_ms.valueauc_microservice_name))
os.system(valueauc_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(valueauc_ms.nav_to_valueauc_target_dir)
os.system(dockercommand.dockerImageBuild(
    valueauc_ms.valueauc_microservice_name))

# Deploy ValueBD Microservice
print('Creating Docker Image for ValueBD micro-service....')
os.chdir(valuebd_ms.nav_to_valuebd_dir)
os.system(springboot.compile_spring_boot_application)
os.system(dockercommand.dockerRemoveImage(
    valuebd_ms.valuebd_microservice_name))
os.system(valuebd_ms.copy_Dockerfile_to_target)
time.sleep(1)
os.chdir(valuebd_ms.nav_to_valuebd_target_dir)
os.system(dockercommand.dockerImageBuild(
    valuebd_ms.valuebd_microservice_name))

# Deploy WorkBD Microservice
print('Creating Docker Image for WorkBD micro-service....')
# os.chdir(wordbd_ms.nav_to_workbd_dir)
# os.system(springboot.compile_spring_boot_application)
# os.system(dockercommand.dockerRemoveImage(
#     wordbd_ms.workbd_microservice_name))
# os.system(wordbd_ms.copy_Dockerfile_to_target)
# time.sleep(1)
# os.chdir(wordbd_ms.nav_to_workbd_target_dir)
# os.system(dockercommand.dockerImageBuild(
#     wordbd_ms.workbd_microservice_name))


# Docker Compose Up
os.chdir(automationScriptDir)
os.system(dockercommand.dockerComposeUp())
