# -*- coding: UTF-8 -*-

import secrets

# Task 1
def generateToken(L):
    return int.from_bytes(secrets.token_bytes(L),'big')

# Task 2
def encodeString(strPlain):
    token = generateToken(len(strPlain))
    tokenBin = bin(token)[2::].zfill(len(str(token))*8)
    strBin = bin(int.from_bytes(strPlain.encode("UTF-8"),'big'))[2::].zfill(len(strPlain)*8)
    strCipher = ''
    print(len(strBin)==len(tokenBin))
    for i in range(len(strBin)-1,1,-1):
        strCipher = strCipher + str(int(strBin[i]) ^ int(token[i]))
    strCipher = strCipher[::-1]
    return [int(tokenBin,base=2),strCipher]
    
def decodeString(tokenBin, strCipher):
    pass


string1 = "路漫漫其修远兮，吾将上下而求索。"
string2 = "Never put off until tomorrow what may be done today."
result = encodeString(string1)
print(result[1])