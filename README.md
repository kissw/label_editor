## Label Editor

Yolo Label 변경 프로그램  </br></br>

## 사용법
### 파라미터
```yaml
load_data_path: ./img
save_txt_path: ./img
```

정의된 함수는 총 4가지
```python
def rebuild_label_coco():
    #COCO Dataset ( person bicycle car motorcycle truck dog )
    ...
def rebuild_label_cluster():
    #Cluster Dataset
    ...
def rebuild_label_ipark():
    #ipark Dataset
    ...
def rebuild_label_dorm():
    #dorm Dataset
    ...
```
### 만약 새로운 클래스가 추가될 경우 label_mapping.csv 클래스 번호 확인
ex) coco dataset 에서 고양이 클래스를 추가하는 상황을 예로 들어보겠음.

```
cd coco-to-yolo
java -jar cocotoyolo.jar "coco/annotations/instances_train2017.json" "/usr/home/madmax/coco/images/train2017/" "person,bicycle,car,motorcycle,truck,dog,cat" "coco/yolo"
```
위 명령어를 실행하면 person,bicycle,car,motorcycle,truck,dog,cat 클래스의 데이터를 다운하고 txt파일도 제작됨.

근데 원본 COCO Dataset 기준으로 클래스 번호가 생성돼서 Person이 0, Bicycle이 1 이런식으로 cat은 6으로 저장될거임.
따라서 label_mapping.csv을 참고하여 cat에 대한 남는 class 번호인 85번을 설정해주면 됨.

그리고 rebuild_label_coco 함수 내용을 아래처럼 변경해주면 됨.
```python
def rebuild_label_coco(self): 

    ...

    if pre_data[0] == "0":
        rebuild_label = "78"
    elif pre_data[0] == "1":
        rebuild_label = "79"
    elif pre_data[0] == "2":
        rebuild_label = "80"
    elif pre_data[0] == "3":
        rebuild_label = "81"
    elif pre_data[0] == "4":
        rebuild_label = "82"
    elif pre_data[0] == "5":
        rebuild_label = "83"
    elif pre_data[0] == "6": # 6은 COCO Dataset에서 고양이 클래스 번호
        rebuild_label = "85" # 85는 우리가 학습시킬 데이터셋에서의 고양이 클래스 번호
    else:
        rebuild_label = pre_data[0]

    ...
    
```
</br>
</br>

### 명령어
conda 환경설치 방법은 상위 폴더를 참조
```
(base) $ conda activate 3w

(3w) $ python src/label_editor.py
```