package com.szecsenyi.animalsounds;

import java.lang.reflect.Field;
import java.util.Locale;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class PictureChooserWithSound extends Activity {

  ImageView[] image = new ImageView[3];
  ImageView refresh;
  Field[] selectedImages = new Field[3];
  MediaPlayer mp;
  String TAG = "as_";
  int image_counter;
  ProgressBar mProgressBar;
  String selectedSoundName;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // Set default layout from xml (3 bagoly) 
    setContentView(R.layout.activity_picture_chooser_with_sound);
    
    
    
    // Osszes kep listazasa   
    Field[] allImageFileList = R.drawable.class.getFields();
    // Azon kepek szamanak kiszamolasa, amik TAG-el kezdodnek 
    int length = 0;
    for(int i=0;i<allImageFileList.length;i++){
      if(allImageFileList[i].getName().startsWith(TAG)){
        length++;
      }
    }
    
    // a TAG-el jelšlt kŽpek ‡ttšltŽse a list-be
    Field[] imageList = new Field[length];
    int list_i = 0;
    for(int i = 0; i<allImageFileList.length;i++){
      if(allImageFileList[i].getName().startsWith(TAG)){
        imageList[list_i++] = allImageFileList[i];
      }
    }
    
    // a harom kep kivalasztasa
    selectedImages[0] = imageList[ (int )(Math.random() * imageList.length)];
    do{
      selectedImages[1] = imageList[ (int )(Math.random() * imageList.length)];
    }while(selectedImages[0].equals(selectedImages[1]));
    do{
      selectedImages[2] = imageList[ (int )(Math.random() * imageList.length)];
    }while(selectedImages[0].equals(selectedImages[2]) || selectedImages[1].equals(selectedImages[2]));
    
   // a kivalasztott kepek feldolgozasa Bitmap-be
    BitmapDrawable[] drawables=new BitmapDrawable[selectedImages.length];
    for (int i=0;i<selectedImages.length;i++){
      try{
        int id=selectedImages[i].getInt(null);
        drawables[i]=(BitmapDrawable)getResources().getDrawable(id);
      } catch (NotFoundException e) {
        e.printStackTrace();
      } catch (IllegalArgumentException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      }
    }

    selectedSoundName = selectedImages[ (int )(Math.random() * selectedImages.length)].getName();
    final Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+selectedSoundName);
    //int resSoundId = 0;
  /*  try {
      Field selectedSound = R.raw.class.getField(selectedSoundName);
      //resSoundId = selectedSound.getInt(null);
      
    } catch (NoSuchFieldException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    */

    // XML Osszekapcsol‡sa java k—ddal
    image[0] = (ImageView) findViewById(R.id.imageView1);
    image[1] = (ImageView) findViewById(R.id.imageView2);
    image[2] = (ImageView) findViewById(R.id.imageView3);
     
   // kep objektumok beallitasa 
   for( image_counter = 0; image_counter<3;image_counter++){
   
     // Kepek beallitasa tšmbbÎl
     image[image_counter].setImageDrawable(drawables[image_counter]);
   
     if(selectedImages[image_counter].getName().equals(selectedSoundName)){
       // Kattint‡sra œjranyitja az aktivit‡st
       image[image_counter].setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
           
            // êgy van, ez a Zene lej‡tsz‡sa
               mp = MediaPlayer.create(PictureChooserWithSound.this, R.raw.igyvaneza);
               mp.setOnCompletionListener(new OnCompletionListener() {
    
                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       mp.release();
                   }
    
               });  
               
               mp.start();
                              
               try {
                Thread.sleep(1500);
              } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              } 
               
               String name = view.getContext().getResources().getResourceEntryName(view.getId());
               
               String nameSoundName; 
               if(name.equals("imageView1")){
                 nameSoundName = selectedImages[ 0].getName();
               }else if(name.equals("imageView2")){
                 nameSoundName = selectedImages[ 1].getName();
               }else if(name.equals("imageView3")){
                 nameSoundName = selectedImages[ 2].getName();
               }
                 
               nameSoundName = selectedSoundName.replace("as_", "asn_"); 
                final Uri uri = Uri.parse("android.resource://"+getPackageName()+"/raw/"+nameSoundName);
               
               
               
            // êgy van, ez a Zene lej‡tsz‡sa
               mp = MediaPlayer.create(PictureChooserWithSound.this, uri);
               mp.setOnCompletionListener(new OnCompletionListener() {

                   @Override
                   public void onCompletion(MediaPlayer mp) {
                       mp.release();
                   }

               });  
               
               mp.start();
               
                              
               try {
                Thread.sleep(1800);
              } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
              } 
               
               finish();
               startActivity(getIntent());
          
           
         }
  
     });
     }
     else {
    // Kattint‡sra œjranyitja az aktivit‡st
       image[image_counter].setOnClickListener(new OnClickListener() {
         public void onClick(View view) {
           
           Locale.getDefault().getDisplayLanguage();
        // "Ez bizony nem a " zene lej‡tsz‡sa
           mp = MediaPlayer.create(PictureChooserWithSound.this, R.raw.eznemaz);
           mp.setOnCompletionListener(new OnCompletionListener() {

               @Override
               public void onCompletion(MediaPlayer mp) {
                   mp.release();
               }

           });   
           mp.start();
          }
  
     });
            }
       
    
     
     
       // Zene lej‡tsz‡sa
       mp = MediaPlayer.create(PictureChooserWithSound.this, uri);
       mp.setOnCompletionListener(new OnCompletionListener() {

           @Override
           public void onCompletion(MediaPlayer mp) {
               mp.release();
           }

       });   
       mp.start();
     
   }
   // Progress Bar inicializ‡l‡s
   //mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
   //mProgressBar.setProgress(100);
   
   //Refresh gomb kezelŽs
   refresh = (ImageView) findViewById(R.id.refresh);
   refresh.setOnClickListener(new OnClickListener() {
     public void onClick(View view) {
       
    //  Zene œjraj‡tsz‡sa
       mp = MediaPlayer.create(PictureChooserWithSound.this, uri);
       mp.setOnCompletionListener(new OnCompletionListener() {

           @Override
           public void onCompletion(MediaPlayer mp) {
               mp.release();
           }

       });   
       mp.start();
      }

     });
   }
   

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.picture_chooser_with_sound, menu);
    return true;
  }

}
