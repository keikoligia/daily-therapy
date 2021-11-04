import math
from datetime import datetime

hora = 15
minuto = 6

now = datetime.now()
ct = now.strftime("%H%M")

agr = int(str(ct))
horario = hora + (minuto/60)

trigger = now - horario
print("Current Time =", current_time)
print(trigger)