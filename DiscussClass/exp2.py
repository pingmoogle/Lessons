# -*- coding: UTF-8 -*-

import secrets
import binascii
import sys

# Task 1
def generateToken(L):
    return int.from_bytes(binascii.hexlify(secrets.token_bytes(L)), 'big')


# Task 2
def generateStrBin(s):
    binStr = ''
    for i in s:
        binStr = binStr + bin(ord(i))[2:].zfill(16)
    return binStr

def encodeString(strPlain):
    token = generateToken(len(strPlain))
    tokenBin = bin(token)[2::].zfill(len(strPlain)*16)
    generateStrBin(strPlain)
    # strBin = bin(int.from_bytes(strPlain.encode("UTF-8"),'big'))[2::].zfill(len(strPlain)*16)
    strBin = generateStrBin(strPlain)
    strCipher = ''
    # print(len(strBin)==len(tokenBin))
    for i in range(len(strBin)):
        strCipher = strCipher + str((int(strBin[i])) ^ (int(tokenBin[i])))

    return [int(tokenBin, base=2), strCipher]


# Task 3
def decodeString(token, strCipher):
    tokenBin = bin(token)[2::].zfill(len(strCipher))
    strPlainDecode = ''
    for i in range(len(strCipher)):
        strPlainDecode = strPlainDecode + \
            str((int(strCipher[i])) ^ (int(tokenBin[i])))

    strFinal = ''
    wordLength = int(len(strPlainDecode)/16)
    for i in range(wordLength):
        tmp = strPlainDecode[i*16:i*16+16]
        ordnum = int(tmp, base=2)
        ch = chr(ordnum)
        strFinal = strFinal + ch

    return strFinal


# Task 4
def stringTest():
    string1 = "路漫漫其修远兮，吾将上下而求索。"
    string2 = "Never put off until tomorrow what may be done today."

    # Encode String
    result_0 = encodeString(string1)
    result_1 = encodeString(string2)
    print("String1 KEY: " + str(result_0[0]))
    print("String1 Cipher: " + result_0[1])
    print("String2 KEY: " + str(result_1[0]))
    print("String2 Cipher: " + result_1[1])
    # Decode String
    result_3 = decodeString(result_0[0], result_0[1])
    result_4 = decodeString(result_1[0], result_1[1])
    print("Decode Result: ")
    print(result_3)
    print(result_4)


# Task 5
def encodeFile(filePath):
    try:
        with open(filePath, 'r', encoding='UTF-8') as f:
            content = f.read()
            result = encodeString(content)
        with open(filePath, 'w') as f:
            f.write(result[1])
            print("File Locked! The password is: ")
            print(result[0])
    except IOError:
        print("Can't open the file: " + filePath)
    finally:
        pass

def decodeFile(filePath, token):
    try:
        with open(filePath, 'r', encoding='UTF-8') as f:
            content = f.read()
            result = decodeString(int(token), content)
        with open(filePath, 'w', encoding='UTF-8') as f:
            f.write(result)
            print("File Unlocked.")   
            
    except IOError:
        print("Can't open the file: " + filePath)
    finally:
        pass



if __name__ == "__main__":
    try:
        if sys.argv[1] == '--encode':
            encodeFile(sys.argv[2])
        elif sys.argv[1] == '--decode':
            decodeFile(sys.argv[2], sys.argv[3])
        elif sys.argv[1] == '--test':
            stringTest()
    except IndexError:
        print(
            "Wrong Arguements.\n"+
            "To run the Test strings to encode and decode, use: --test\n"+
            "To encode a File, use: --encode FilePath\n"+
            "To decode a File, use: --decode FilePath Password"
        )
    finally:
        pass
