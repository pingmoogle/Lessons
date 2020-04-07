# -*- coding: UTF-8 -*-

from Crypto.Hash import SHA
from Crypto.PublicKey import RSA
from Crypto.Signature import PKCS1_v1_5
import base64

def generateKey():
    rsa = RSA.generate(1024)

    private_pem = rsa.exportKey()
    public_pem = rsa.publickey().exportKey()

    with open('id_rsa', 'wb') as f:
        f.write(private_pem)
    with open('id_rsa.pub', 'wb') as f:
        f.write(public_pem)

def signFile(filePath):
    with open(filePath, 'r', encoding="UTF-8") as f:
        content = f.read()
        content = bytes(content, encoding="UTF-8")
    with open("id_rsa",'r') as f:
        key = f.read()
        rsakey = RSA.importKey(key)
        signer = PKCS1_v1_5.new(rsakey)
        digest = SHA.new()
        digest.update(content)
        sign = signer.sign(digest)
        signature = base64.b64encode(sign)
    with open("signature",'w') as f:
        f.write(str(signature, encoding="UTF-8"))
    print(signature)
    return signature

def verifySign(filePath,signature):
    with open(filePath, 'r', encoding="UTF-8") as f:
        message = f.read()
        message = bytes(message, encoding="UTF-8")
    with open('id_rsa.pub') as f:
        key = f.read()
        rsakey = RSA.importKey(key)
        verifier = PKCS1_v1_5.new(rsakey)
        digest = SHA.new()

        digest.update(message)
        is_verify = verifier.verify(digest, base64.b64decode(signature))

        print(is_verify)


if __name__ == "__main__":
    generateKey()
    fPath = "C:\\Users\\SUNWEI\\Documents\\GitHub\\Lessons\\DiscussClass\\exp4.txt"
    s = signFile(fPath)
    verifySign(fPath, s)
