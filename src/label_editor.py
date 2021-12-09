import cv2
import os, sys
import multiprocessing
import yaml

class LabelEditor():
    def __init__(self):
        ### load param
        try:
            config_name = './param/' + 'label_editor.yaml'
            with open(config_name) as file:
                self.yaml = yaml.load(file, Loader=yaml.FullLoader)
        except:
            exit('ERROR: label_editor.yaml not defined.') 
            
        self.load_data_path = self.yaml['load_data_path']
        self.save_txt_path = self.yaml['save_txt_path']
        self.img_filename = []
        self.process_task = []
        self.load_images_from_folder()
        
    def load_images_from_folder(self):
        img_filename = []
        file_num = len(os.listdir(self.load_data_path))
        count = 1
        for filename in os.listdir(self.load_data_path):
            if ".txt" in filename:
                img_filename.append(filename)
                cur_output = 'image loading ... {0:6d}/{1:6d}\r'.format(count, file_num)
                sys.stdout.write(cur_output)
                sys.stdout.flush()
            count += 1
        self.img_filename = img_filename
    
    def rebuild_label_coco(self):
        file_num = len(self.img_filename)
        for i in range(0,file_num):
            rename = self.img_filename[i].split(".txt")
            fr = open(self.load_data_path+'/'+rename[0]+".txt", mode='r')
            lines = fr.readlines()
            label_data = []
            for j in range(0, len(lines)):
                rebuild_label = ""
                pre_data = lines[j].split(" ")
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
                else:
                    rebuild_label = pre_data[0]
                    
                label_data.append(rebuild_label+" "+pre_data[1]+" "+pre_data[2]+" "+pre_data[3]+" "+pre_data[4])
                
            fw = open(self.save_txt_path+'/'+rename[0]+".txt", mode='w')
            fw.writelines(label_data)
            fr.close()
            fw.close()
            
            cur_output = 'txt generating ... {0:6d}/{1:6d}\r'.format(i, file_num)
            sys.stdout.write(cur_output)
            sys.stdout.flush()
    
    def rebuild_label_ipark(self):
        file_num = len(self.img_filename)
        for i in range(0,file_num):
            rename = img_filename[i].split(".txt")
            fr = open(self.load_data_path+'/'+rename[0]+".txt", mode='r')
            lines = fr.readlines()
            label_data = []
            for i in range(0, len(lines)):
                rebuild_label = ""
                pre_data = lines[i].split(" ")
                
                if pre_data[0] == "0":
                    rebuild_label = "10"
                elif pre_data[0] == "1":
                    rebuild_label = "8"
                elif pre_data[0] == "2":
                    rebuild_label = "18"
                elif pre_data[0] == "3":
                    rebuild_label = "20"
                elif pre_data[0] == "4":
                    rebuild_label = "11"
                elif pre_data[0] == "5":
                    rebuild_label = "9"
                elif pre_data[0] == "6":
                    rebuild_label = "19"
                elif pre_data[0] == "7":
                    rebuild_label = "21"
                elif pre_data[0] == "8":
                    rebuild_label = "0"
                elif pre_data[0] == "9":
                    rebuild_label = "1"
                elif pre_data[0] == "10":
                    rebuild_label = "4"
                elif pre_data[0] == "11":
                    rebuild_label = "5"
                elif pre_data[0] == "12":
                    rebuild_label = "6"
                elif pre_data[0] == "13":
                    rebuild_label = "7"
                else:
                    rebuild_label = pre_data[0]
                
                label_data.append(rebuild_label+" "+pre_data[1]+" "+pre_data[2]+" "+pre_data[3]+" "+pre_data[4])
                
            fw = open(self.save_txt_path+'/'+rename[0]+".txt", mode='w')
            fw.writelines(label_data)
            fr.close()
            fw.close()
            
            cur_output = 'txt generating ... {0:6d}/{1:6d}\r'.format(i, file_num)
            sys.stdout.write(cur_output)
            sys.stdout.flush()
    
    def rebuild_label_cluster(self):
        file_num = len(self.img_filename)
        for i in range(0,file_num):
            rename = img_filename[i].split(".txt")
            fr = open(self.load_data_path+'/'+rename[0]+".txt", mode='r')
            lines = fr.readlines()
            label_data = []
            for i in range(0, len(lines)):
                rebuild_label = ""
                pre_data = lines[i].split(" ")
                
                if pre_data[0] == "0":
                    rebuild_label = "0"
                elif pre_data[0] == "1":
                    rebuild_label = "1"
                elif pre_data[0] == "2":
                    rebuild_label = "2"
                elif pre_data[0] == "3":
                    rebuild_label = "3"
                elif pre_data[0] == "4":
                    rebuild_label = "8"
                elif pre_data[0] == "5":
                    rebuild_label = "9"
                elif pre_data[0] == "6":
                    rebuild_label = "18"
                elif pre_data[0] == "7":
                    rebuild_label = "19"
                elif pre_data[0] == "8":
                    rebuild_label = "20"
                elif pre_data[0] == "9":
                    rebuild_label = "21"
                elif pre_data[0] == "10":
                    rebuild_label = "22"
                elif pre_data[0] == "11":
                    rebuild_label = "23"
                elif pre_data[0] == "12":
                    rebuild_label = "24"
                elif pre_data[0] == "13":
                    rebuild_label = "25"
                elif pre_data[0] == "14":
                    rebuild_label = "26"
                elif pre_data[0] == "15":
                    rebuild_label = "27"
                elif pre_data[0] == "16":
                    rebuild_label = "28"
                elif pre_data[0] == "17":
                    rebuild_label = "29"
                elif pre_data[0] == "18":
                    rebuild_label = "4"
                elif pre_data[0] == "19":
                    rebuild_label = "6"
                elif pre_data[0] == "20":
                    rebuild_label = "5"
                elif pre_data[0] == "21":
                    rebuild_label = "7"
                elif pre_data[0] == "22":
                    rebuild_label = "84"
                else:
                    rebuild_label = pre_data[0]
                
                label_data.append(rebuild_label+" "+pre_data[1]+" "+pre_data[2]+" "+pre_data[3]+" "+pre_data[4])
                
            fw = open(self.save_txt_path+'/'+rename[0]+".txt", mode='w')
            fw.writelines(label_data)
            fr.close()
            fw.close()
            
            cur_output = 'txt generating ... {0:6d}/{1:6d}\r'.format(i, file_num)
            sys.stdout.write(cur_output)
            sys.stdout.flush()
    
    def rebuild_label_dorm(self):
        file_num = len(self.img_filename)
        for i in range(0,file_num):
            rename = img_filename[i].split(".txt")
            fr = open(self.load_data_path+'/'+rename[0]+".txt", mode='r')
            lines = fr.readlines()
            label_data = []
            for i in range(0, len(lines)):
                rebuild_label = ""
                pre_data = lines[i].split(" ")
                
                if pre_data[0] == "0":
                    rebuild_label = "18"
                elif pre_data[0] == "1":
                    rebuild_label = "20"
                elif pre_data[0] == "2":
                    rebuild_label = "22"
                elif pre_data[0] == "3":
                    rebuild_label = "24"
                elif pre_data[0] == "4":
                    rebuild_label = "26"
                elif pre_data[0] == "5":
                    rebuild_label = "28"
                elif pre_data[0] == "6":
                    rebuild_label = "30"
                elif pre_data[0] == "7":
                    rebuild_label = "32"
                elif pre_data[0] == "8":
                    rebuild_label = "34"
                elif pre_data[0] == "9":
                    rebuild_label = "36"
                elif pre_data[0] == "10":
                    rebuild_label = "19"
                elif pre_data[0] == "11":
                    rebuild_label = "21"
                elif pre_data[0] == "12":
                    rebuild_label = "23"
                elif pre_data[0] == "13":
                    rebuild_label = "25"
                elif pre_data[0] == "14":
                    rebuild_label = "27"
                elif pre_data[0] == "15":
                    rebuild_label = "29"
                elif pre_data[0] == "16":
                    rebuild_label = "31"
                elif pre_data[0] == "17":
                    rebuild_label = "33"
                elif pre_data[0] == "18":
                    rebuild_label = "35"
                elif pre_data[0] == "19":
                    rebuild_label = "37"
                elif pre_data[0] == "20":
                    rebuild_label = "0"
                elif pre_data[0] == "21":
                    rebuild_label = "1"
                elif pre_data[0] == "22":
                    rebuild_label = "4"
                elif pre_data[0] == "23":
                    rebuild_label = "5"
                elif pre_data[0] == "24":
                    rebuild_label = "6"
                elif pre_data[0] == "25":
                    rebuild_label = "7"
                else:
                    rebuild_label = pre_data[0]
                
                label_data.append(rebuild_label+" "+pre_data[1]+" "+pre_data[2]+" "+pre_data[3]+" "+pre_data[4])
                
            fw = open(self.save_txt_path+'/'+rename[0]+".txt", mode='w')
            fw.writelines(label_data)
            fr.close()
            fw.close()
            
            cur_output = 'txt generating ... {0:6d}/{1:6d}\r'.format(i, file_num)
            sys.stdout.write(cur_output)
            sys.stdout.flush()
    
def main():
    da = LabelEditor()
    da.rebuild_label_coco()
    # da.rebuild_label_cluster()
    # da.rebuild_label_ipark()
    # da.rebuild_label_dorm()


if __name__ == '__main__':
    if len(sys.argv) < 1:
        print('Usage: ')
        exit('$ python label_editor.py')

    main()