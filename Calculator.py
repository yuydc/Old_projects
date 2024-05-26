def summation(a, b):
    while b != 0:
        carry = a & b
        a = a ^ b
        b = carry << 1
    return a

def subtraction(a, b):
    while b != 0:
        borrow = (~a) & b
        a = a ^ b
        b = borrow << 1
    return a

def negate(a):
    return summation(~a, 1)

def abs(a):
    return a if a >= 0 else negate(a)

def multiplication(a, b):
    result = 0
    positive = b > 0
    b = abs(b)
    for i in range(b):
        result = summation(result, a)
    return result if positive else negate(result)

def division(a, b):
    if b == 0:
        raise ValueError("Cannot divide by zero")

    positive = (a > 0) == (b > 0)
    a = abs(a)
    b = abs(b)

    count = 0
    sum_b = b
    while a >= b:
        a = subtraction(a, b)
        count = summation(count, 1)

    return count if positive else negate(count)

def power(base, exp):
    if exp == 0:
        return 1
    result = base
    for _ in range(exp - 1):
        result = multiplication(result, base)
    return result

def square(a):
    return multiplication(a, a)

def cube(a):
    return multiplication(a, multiplication(a, a))

while True:
    print("calculator with just bitwise operators")
    print("1. Summation")
    print("2. Subtraction")
    print("3. Multiplication")
    print("4. Division")
    print("5. Power")
    print("6. Square")
    print("7. Cube")
    print("8. Exit")
    
    choice = input("Choose an operation (1 to 14): ")

    if choice == '8':
        break
    
    if choice in {'1', '2', '3', '4', '5'}:
        a = int(input("Enter first number: "))
        b = int(input("Enter second number: "))

    if choice == '1':
        print("Result:", summation(a, b))
    elif choice == '2':
        print("Result:", subtraction(a, b))
    elif choice == '3':
        print("Result:", multiplication(a, b))
    elif choice == '4':
        print("Result:", division(a, b))
    elif choice == '5':
        print("Result:", power(a, b))
    elif choice == '6':
        a = int(input("Enter the number: "))
        print("Result:", square(a))
    elif choice == '7':
        a = int(input("Enter the number: "))
        print("Result:", cube(a))
