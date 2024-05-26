import tkinter as tk
from tkinter import messagebox

BOARD_EMPTY = 0
BOARD_PLAYER_X = 1
BOARD_PLAYER_O = -1

def player(s):
    if s.count(BOARD_PLAYER_X) + s.count(BOARD_PLAYER_O) == 9:
        return None
    elif s.count(BOARD_PLAYER_X) > s.count(BOARD_PLAYER_O):
        return BOARD_PLAYER_O
    else:
        return BOARD_PLAYER_X

def actions(s):
    play = player(s)
    actions_list = [(play, i) for i in range(len(s)) if s[i] == BOARD_EMPTY]
    return actions_list

def result(s, a):
    (play, index) = a
    s_copy = s.copy()
    s_copy[index] = play
    return s_copy

def terminal(s):
    for i in range(3):
        if s[3 * i] == s[3 * i + 1] == s[3 * i + 2] != BOARD_EMPTY:
            return s[3 * i]
        if s[i] == s[i + 3] == s[i + 6] != BOARD_EMPTY:
            return s[i]

    if s[0] == s[4] == s[8] != BOARD_EMPTY:
        return s[0]
    if s[2] == s[4] == s[6] != BOARD_EMPTY:
        return s[2]

    if player(s) is None:
        return 0
    
    return None

def utility(s, cost):
    term = terminal(s)
    if term is not None:
        return (term, cost)
    
    action_list = actions(s)
    utils = []
    for action in action_list:
        new_s = result(s, action)
        utils.append(utility(new_s, cost + 1))

    score = utils[0][0]
    idx_cost = utils[0][1]
    play = player(s)
    if play == BOARD_PLAYER_X:
        for i in range(len(utils)):
           if utils[i][0] > score:
                score = utils[i][0]
                idx_cost = utils[i][1]
    else:
        for i in range(len(utils)):
           if utils[i][0] < score:
                score = utils[i][0]
                idx_cost = utils[i][1]
    return (score, idx_cost) 

def minimax(s):
    action_list = actions(s)
    utils = []
    for action in action_list:
        new_s = result(s, action)
        utils.append((action, utility(new_s, 1)))

    if len(utils) == 0:
        return ((0, 0), (0, 0))

    sorted_list = sorted(utils, key=lambda l: l[0][1])
    action = min(sorted_list, key=lambda l: l[1])
    return action

def check_winner(s):
    term = terminal(s)
    if term == BOARD_PLAYER_X:
        return "You have won!"
    elif term == BOARD_PLAYER_O:
        return "You have lost!"
    elif term == 0:
        return "It's a tie."
    else:
        return None

def make_move(index):
    global s, buttons, game_over
    
    if game_over or s[index] != BOARD_EMPTY:
        return
    
    buttons[index].config(text='X', state=tk.DISABLED)
    s = result(s, (1, index))

    winner = check_winner(s)
    if winner is not None:
        messagebox.showinfo("Game Over", winner)
        game_over = True
        return

    action = minimax(s)
    s = result(s, action[0])
    buttons[action[0][1]].config(text='O', state=tk.DISABLED)

    winner = check_winner(s)
    if winner is not None:
        messagebox.showinfo("Game Over", winner)
        game_over = True
        return

def reset_game():
    global s, buttons, game_over
    s = [BOARD_EMPTY] * 9
    game_over = False
    for button in buttons:
        button.config(text='', state=tk.NORMAL)

def create_buttons():
    global buttons
    buttons = []
    for i in range(9):
        button = tk.Button(root, text='', width=10, height=5, command=lambda i=i: make_move(i))
        button.grid(row=i // 3, column=i % 3)
        buttons.append(button)

root = tk.Tk()
root.title("Tic Tac Toe")

s = [BOARD_EMPTY] * 9
game_over = False

create_buttons()

reset_button = tk.Button(root, text="Reset", command=reset_game)
reset_button.grid(row=3, columnspan=3)

root.mainloop()
