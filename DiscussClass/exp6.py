# -*- coding: UTF-8 -*-

import sys
import os


def deleteFile(dir):
    if os.path.isdir(dir):
        dir_lists = os.listdir(dir)
        for get_file in dir_lists:
            get_file=os.path.join(dir,get_file)
            if os.path.isdir(get_file):
                deleteFile(get_file)
            else:
                result=os.path.splitext(get_file)
                if ((result[1] in format) or (os.path.getsize(get_file) == 0)):
                    os.remove(get_file)
                    print(get_file+"  deleted....")



if __name__ == "__main__":
    format=['.tmp','.log','.obj','.txt']
    try:
        deleteFile(sys.argv[1])
    except IndexError:
        print("An path is needed.")
    finally:
        pass
    