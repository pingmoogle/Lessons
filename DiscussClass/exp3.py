# -*- coding: UTF-8 -*-

import re
import string
from random import choice
from zipfile import ZipFile as zf


def generateToken(L):
    chars=string.ascii_letters+string.digits
    return ''.join([choice(chars) for i in range(L)])

def checkToken(tokenStr):
    result = re.match(r"(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,}",tokenStr)
    if result == None:
        print("False")
    else:
        print("True")

def unzipFile(filePath, token=''):
    try:
        z = zf(filePath)
        print(z.namelist())
        fileInZip = z.open('part1.txt',pwd=token.encode("UTF-8"))
        content = str(fileInZip.read(),encoding="UTF-8")
        print(content)
    except FileNotFoundError:
        print("Sorry. File Not Found.")
    except RuntimeError:
        print("Sorry. Password incorrect.")
    finally:
        pass


if __name__ == "__main__":
    # 生成10个密码
    for i in range(10):
        tokenUse = generateToken(4+i)
        print(tokenUse)

        # 检查这个密码是否符合规则
        checkToken(tokenUse)

    # 解压一个zip文件
    unzipFile("C:\\Users\\SUNWEI\\Documents\\GitHub\\Lessons\\DiscussClass\\exp3.zip","PSwd1234")
