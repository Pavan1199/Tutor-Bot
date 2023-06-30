# DOCKER COMMANDS

DOCKER_DEFAULT_PORT = '8080'
DOCKER_DEMON_MODE = '-d'


def dockerListImages():
    return 'docker image ls'


def dockerRemoveImage(IMAGE_NAME):
    return 'docker image rm ' + IMAGE_NAME + ' --force'


def dockerImageBuild(IMAGE_NAME):
    return 'docker build -t ' + IMAGE_NAME + ' .'


def dockerRunImage(IMAGE_NAME, WILL_RUN_PORT, MODE):
    return 'docker run ' + MODE + ' -p ' + WILL_RUN_PORT + ':' + DOCKER_DEFAULT_PORT + ' ' + IMAGE_NAME


def dockerStopProcess(CONTAINER_ID):
    return 'docker stop ' + CONTAINER_ID


def dockerStopAllProcesses():
    return 'docker stop $(docker ps -a -q)'


def dockerRemoveAllContainers():
    return 'docker rm $(docker ps -a -q)'


def dockerComposeUp():
    return 'docker-compose up -d'
