using namespace std;
#include<windows.h>
#include<GL/glut.h>
#include<stdlib.h>
#include<math.h>
#include<conio.h>
#include<stdio.h>
#include <iostream>
#include <iomanip>

/*
* 
* -Thomas Kopcho
* -Sept 24 2020
This program loads in an icon, a polyman. This program will utilize the ModelView Matrix in order to animate the icon.
*/

//-------GLOBAL VALUES-------//
//global angular value
float theta = 0.0;
//global sclaing value
float scale1 = 1.0;
//global movement value for dx and dy
float dx = 6.0, dy = -3.0;
float thetaF = 0.0;
float dfx = 6.0, dfy = -3.0;

int frame = 1;
//initializes the window
void init(void);
//draws a square in open window
void RenderScene(void);
//loads in the PolyMan icon
void loadPolyIcon(float[], float[], float[], float[]);
//draws the Polymanicon
void drawPolyIcon(float[], float[]);
//draws polyfeet
void drawPolyFeet(float[], float[]);
void setMatrixBody(void);
void setMatrixFeet(void);

//sets up the clear color
void SetupRC(void);
//this function is called every 30ms and updates the icon
void TimerFunction(int);
//Main Program

int main(int argc, char** argv) {
     //set up window title

     char header[] = "Kopcho PolyMan Animation";
     glutInit(&argc, argv);
     //Sets window size and postion
     glutInitWindowSize(840, 640);
     glutInitWindowPosition(140, 20);
     SetupRC();
     //opens and labels window
     glutCreateWindow(header);
     glutDisplayFunc(RenderScene);
     glutTimerFunc(30, TimerFunction, 1);
     //Draws the scene
     glutMainLoop();
     return 0;
}
void RenderScene(void) {
     float xdel = 0.25;
     float px[9], py[9], pfx[6], pfy[6];

     cout << "In RenderScene" << endl;
     //set the drawing color to white
     glColor3f(1.0, 1.0, 1.0);
     glMatrixMode(GL_PROJECTION);
     glLoadIdentity();
     //viewport dimensions
     glViewport(0, 0, 840, 640);
     //establish clipping volume in user coordinates
     glOrtho(-7.0, 7.0, -7.0, 7.0, 1.0, -1.0);
     //load untransformed polyman
     loadPolyIcon(px, py, pfx, pfy);
     glClear(GL_COLOR_BUFFER_BIT);
     setMatrixBody();
     drawPolyIcon(px,py);
     setMatrixFeet();
     drawPolyFeet(pfx, pfy);
     glColor3f(1.0, 1.0, 1.0);
     glEnd();
     glutSwapBuffers();

}//end renderscene
void loadPolyIcon(float px[], float py[], float pfx[], float pfy[]) {
     //loads the body
     px[0] = -1.125;     py[0] = 0.0;
     px[1] = -0.625;     py[1] = 0.75;
     px[2] = 0.625;      py[2] = 0.75;
     px[3] = 1.125;      py[3] = 0.0;
     px[4] = 0.625;      py[4] = -0.75;
     px[5] = -0.625;     py[5] = -0.75;
     //loads the closed mouth
     px[6] = -0.375;    py[6] = 0.0;
     px[7] = -0.775;    py[7] = -0.5;
     //loads the eye
     px[8] = -0.5;        py[8] = 0.5;
     //loads the feet 
     pfx[0] = -0.25;     pfy[0] = -0.5;
     pfx[1] = -0.25;     pfy[1] = -1.0;
     pfx[2] = -0.5;      pfy[2] = -1.0;
     pfx[3] = 0.25;      pfy[3] = -0.5;
     pfx[4] = 0.25;      pfy[4] = -1.0;
     pfx[5] = 0;         pfy[5] = -1.0;

     return;
}//end of loadPolyIcon
void drawPolyIcon(float pxp[], float pyp[]) {
     int i;

     cout << "inDrawPolyIcon" << endl;
     //draws first point of the body
     glBegin(GL_LINE_LOOP);
     glVertex2f(pxp[0], pyp[0]);
     //draws the rest of points for body
     //if not in frame with open mouth. draw closed mouth poly
     if (frame != 2 && frame != 3 && frame != 4) {
          for (i = 1; i <= 5; i++) {
               glVertex2f(pxp[i], pyp[i]);
          }
          glEnd();
          glFlush();
          glColor3f(0.1, 0.1, 0.6);
          glShadeModel(GL_FLAT);
          glBegin(GL_POLYGON);

          glVertex2f(pxp[0], pyp[0]);
          glVertex2f(pxp[1], pyp[1]);
          glVertex2f(pxp[2], pyp[2]);
          glVertex2f(pxp[3], pyp[3]);
          glVertex2f(pxp[4], pyp[4]);
          glVertex2f(pxp[5], pyp[5]);
          glEnd();
          glFlush();
         
          //draws the mouth
          
          glBegin(GL_LINE_STRIP);
          glColor3f(1.0, 0.0, 0.0);
          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[7], pyp[7]);
          glEnd();
          glFlush();
     }

     //if in a frame where his mouth is open, change some points and draw him
     else if (frame == 2 || frame == 3 || frame == 4) {
          pxp[0] = -1.125;     pyp[0] = 0.0;
          pxp[1] = -0.625;     pyp[1] = 0.75;
          pxp[2] = 0.625;      pyp[2] = 0.75;
          pxp[3] = 1.125;      pyp[3] = 0.0;
          pxp[4] = 0.625;      pyp[4] = -0.75;
          //loads the open mouth
          pxp[5] = -0.625;    pyp[5] = -0.75;
          pxp[6] = -0.375;    pyp[6] = 0.0;
          glBegin(GL_POLYGON);
          glVertex2f(pxp[0], pyp[0]);
          for (i = 1; i <= 6; i++) {
               glVertex2f(pxp[i], pyp[i]);
          }
          glEnd();
          glFlush();
          //breaks polyman into triangles since GL_POLYGON cannot fill concave polygons correctly
          glColor3f(0.1, 0.1, 0.6);
          glShadeModel(GL_FLAT);
          glBegin(GL_TRIANGLE_STRIP);

          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[5], pyp[5]);
          glVertex2i(pxp[4], pyp[4]);

          glVertex2f(pxp[2], pyp[2]);
          glVertex2f(pxp[5], pyp[5]);
          glVertex2f(pxp[4], pyp[4]);

          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[4], pyp[4]);
          glVertex2f(pxp[3], pyp[3]);

          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[3], pyp[3]);
          glVertex2f(pxp[2], pyp[2]);

          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[2], pyp[2]);
          glVertex2f(pxp[1], pyp[1]);

          glVertex2f(pxp[6], pyp[6]);
          glVertex2f(pxp[1], pyp[1]);
          glVertex2f(pxp[0], pyp[0]);





          glEnd();
     }
          //draws the eye
          glColor3f(1.0, 0.0, 0.0);
          glBegin(GL_POINTS);
          glVertex2f(pxp[8], pyp[8]);
          glEnd();
          glFlush();
     return;
}//end of drawPolyIcon
//this functions draws the feet. This is needed because the feet are animated differently than the body
void drawPolyFeet(float pfxp[], float pfyp[]) {
     int i;
     glColor3f(1.0, 0.0, 0.0);
     //draws the feet
     glBegin(GL_LINE_STRIP);
     glVertex2f(pfxp[0], pfyp[0]);
     for (i = 1; i <= 2; i++) {
          glVertex2f(pfxp[i], pfyp[i]);
     }
     glEnd();
     glFlush();
     //draws second feet
     glBegin(GL_LINE_STRIP);
     glVertex2f(pfxp[3], pfyp[3]);
     for (i = 4; i <= 5; i++) {
          glVertex2f(pfxp[i], pfyp[i]);
     }
     glEnd();
     glFlush();
     return;
}

//this sets the MODELVIEW Matric for polyman
void setMatrixBody(void) {

     cout << "In setMatrixBody" << endl;

     glMatrixMode(GL_MODELVIEW);
     glLoadIdentity();
     glTranslatef(dx, dy, 0.0);
     glRotatef(theta, 0.0, 0.0, 1.0);
     return;
}
//set MODELVIEW Matrix for the feet of polyman, since those will be animated seperate from the body
void setMatrixFeet(void) {

     cout << "In setMatrixFeet" << endl;

     glMatrixMode(GL_MODELVIEW);
     glLoadIdentity();
     glTranslatef(dfx, dfy, 0.0);
     glRotatef(thetaF, 0.0, 0.0, 0.5);
     return;
}

//Set up Rendering State
void SetupRC(void) {
     glClearColor(0.0, 0.0, 1.0, 1.0);
     return;
}//end of SetupRc

//function Timer
void TimerFunction(int value) {
     switch (frame) {
     case 1:
          //moves polyman to the right
          dx -= 0.12;
          dfx -= 0.12;
          thetaF += 5.0;
          //animation for feet
          while (thetaF > 25.0) {
                    thetaF = -25.0;
               }
          if (dx <= 0.0) {
               dx = 0.0;
               dfx = 0.0;
               frame = 2;
          }
          break;
     case 2: 
          //make polyman go up
          thetaF = 0.0;
          dy += 0.12;
          dfy += 0.12;
          if (dy > 5.0) {
               dy = 5.0;
               dfy = 5.0;
               frame = 3;
          }
          break;
     case 3:
          //makes polyman do flip
          theta += 5.0;
          thetaF += 5.0;
          if (theta >= 360.0) {
               frame = 4;
               theta = 0.0;
               thetaF = 0.0;
          }
          break;
     case 4:
          //polyman falls
          dy -= 0.12;
          dfy -= 0.12;
          if (dy < -3.0) {
               dy = -3.0;
               dfy = -3.0;
               frame = 5;
          }
          break;
     case 5:
          //polyman leaves
          dx -= 0.12;
          dfx -= 0.12;
          thetaF += 5.0;
          while (thetaF > 25.0) {
               thetaF = -25.0;
          }
          if (dx <= -5.5) {
               dx = -5.5;
               dfx = -5.5;
          }
          break;
     }
     glutPostRedisplay();
     glutTimerFunc(30, TimerFunction, 1);
}