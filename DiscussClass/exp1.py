# -*- coding: UTF-8 -*-

def question():
    s = input("Enter your string: ")
    action = input("Enter 1 for decoding or 2 for encoding: ")
    step = input("Enter the password code.(a number): ")
    if action == '1':
        s_plain = decoding(s,int(step)%26)
        print("reuslt:\n" + s_plain)
    elif action == '2':
        s_cipher = encoding(s,int(step)%26)
        print("reuslt:\n" + s_cipher)
    else:
        print("Sorry. Wrong input.")

def decoding(s,step):
    d = {}
    for c in (65, 97):
        for i in range(26):
            d[chr(i+c)] = chr((i + int(step)) % 26 + c)
    s_plain = "".join([d.get(c, c) for c in s])
    return s_plain

def encoding(s,step):
    d = {}
    for c in (65, 97):
        for i in range(26):
            d[chr(i+c)] = chr((i - int(step)) % 26 + c)
    s_cipher = "".join([d.get(c, c) for c in s])
    return s_cipher


if __name__ == "__main__":
    question()

