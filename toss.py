import numpy as np
import matplotlib.pyplot as plt

num_flips = 100000

num_tests = 10000

flips = np.random.randint(2, size=(num_tests, num_flips))

diffs = np.sum(flips, axis=1) - np.sum(1 - flips, axis=1)

plt.figure(figsize=(10, 6))
plt.hist(diffs, bins=range(min(diffs), max(diffs) + 2), alpha=0.7, color='skyblue', edgecolor='black')
plt.title('Difference between Heads and Tails in 10,000 Test Cases of 100,000 Coin Flips Each')
plt.xlabel('Difference (Heads - Tails)')
plt.ylabel('Frequency')
plt.grid(True)
plt.show()
