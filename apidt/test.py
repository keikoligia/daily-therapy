import cv2 
import numpy as np

imagem = cv2.imread (".\img\opencv.png", cv2.IMREAD_COLOR)
cv2.namedWindow("cv")
cv2.imshow ("cv", imagem) 
cv2.waitKey(0)
cv2.destroyAllWindows()