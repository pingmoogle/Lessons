import numpy as np
import math
import matplotlib.pyplot as plt

fig = plt.figure(figsize=(10,6))
ax = plt.subplot(111, polar=True)
ax.set_theta_direction(-1)
ax.set_theta_zero_location('N')
r = np.arange(100, 800, 20)
theta = np.linspace(0, np.pi*2, len(r), endpoint=False)
ax.bar(
    theta,
    r,
    width=0.18, 
    color=np.random.random( (len(r),3)),
    align='edge', 
    bottom=100
)

plt.show()