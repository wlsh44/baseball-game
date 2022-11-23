import random

def generate_num(): #랜덤한 숫자만드는 함수
    num=['0','0','0',]  #숫자 담을 리스트
    newnum=0
    num[0]=str(random.randrange(1,10,1))
    num[1]=str(random.randrange(1,10,1))
    num[2]=str(random.randrange(1,10,1))
    print("랜덤한 숫자 선택")
    
    while(num[0] == num[1]):
        num[1] = str(random.randrange(1, 10, 1))
    while(num[0] == num[2] or num[1] == num[2]):
        num[2] = str(random.randrange(1, 10, 1))
    print (num)    
    return num

rightanswer=generate_num()
tries=0
strike_number = 0
ball_number = 0 

print("숫자 야구 게임을 시작합니다.")
while (strike_number < 3):
    
    
    number = str(input("숫자를 입력해주세요 :"))

    strike_number = 0
    ball_number = 0 
    

    for i in range(0, 3): 
        for j in range(0, 3):
            if(number[i] == str(rightanswer[j]) and i == j):
                strike_number += 1
            elif(number[i] == str(rightanswer[j]) and i != j):
                ball_number += 1
    
    if(strike_number==3):
        print("3스트라이크")
        print("3개의 숫자를 모두 맞히셨습니다! 게임 종료")
    
    elif(strike_number==0 and ball_number==0):
           print("낫싱") 
    else:              
        print("결과: ",strike_number,"스트라이크 ",ball_number,"볼")
    tries += 1
    
print("정답 {}번 만에 맞췄습니다.".format(tries))






