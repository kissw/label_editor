#!/bin/bash

### Recommended to run 'nohup ./<this_script> &' to prevent interruption from SSH session termination.

wait_to_finish() {
    for pid in "${download_pids[@]}"; do
        while kill -0 "$pid"; do
            sleep 30
        done
    done
}

### Dependencies ###

# Misc. utilities
echo "Installing git."
sudo apt-get -q -y install git
sudo apt-get -q -y install unzip

# CUDA

CUDA_REPO_PKG="cuda-repo-ubuntu1604_8.0.61-1_amd64.deb"
wget http://developer.download.nvidia.com/compute/cuda/repos/ubuntu1604/x86_64/${CUDA_REPO_PKG}
sudo dpkg -i ${CUDA_REPO_PKG}
sudo apt-get update
sudo apt-get -y install cuda

# Update for default OS specific package manager.
# sudo yum -y install java-1.8.0
# sudo yum -y remove java-1.7.0-openjdk

### Darknet YOLO ###

git clone https://github.com/pjreddie/darknet.git darknet

mkdir -p coco/images/ coco/annotations/

download_pids=()

### 2017 COCO Dataset ###

echo "Downloading COCO dataset..."
curl -OL "http://images.cocodataset.org/zips/train2017.zip"  &
download_pids+=("$!")
curl -OL "http://images.cocodataset.org/zips/val2017.zip" &
download_pids+=("$!")
curl -OL "http://images.cocodataset.org/annotations/annotations_trainval2017.zip" &
download_pids+=("$!")

wait_to_finish download_pids

inflate_pids=()

unzip 'train2017.zip' -d coco/images/ &
inflate_pids+=("$!")
unzip 'val2017.zip' -d coco/images/ &
inflate_pids+=("$!")
unzip 'annotations_trainval2017.zip' -d coco/annotations/ & # Inflates to 'coco/annotations'.
inflate_pids+=("$!")

wait_to_finish inflate_pids

mkdir -p coco/labels

### Annotation Converter ###
curl -OL "http://commecica.com/wp-content/uploads/2018/07/cocotoyolo.jar"

java -jar cocotoyolo.jar "$PWD/coco/annotations/instances_train2017.json" "$PWD/coco/images/train2017/" "car,truck,bus" "$PWD/coco/yolo/"

mv coco/yolo/image_list.txt coco/train2017_images.txt
mv coco/yolo/ coco/labels/train2017/

java -jar cocotoyolo.jar "$PWD/coco/annotations/instances_val2017.json" "$PWD/coco/images/val2017/" "car,truck,bus" "$PWD/coco/yolo/"

mv coco/yolo/image_list.txt coco/val2017_images.txt
mv coco/yolo/ coco/labels/val2017/