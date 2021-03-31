# Testing-library
A repo for different projects, some of which are unfinished, in one application. The point was to gain as much experience with different features of android programming. 

Android java project.

The whole app has a menu with buttons which take the user into the respective projects. It's the mainActivity.

Built with Gradle

**Short summaries of every project:**

**Image Rotation (imageRotationTestActivity)**
The image should rotate according to he finger movement of the user. The rotation doesn't work completely.

**SoundTesting(SoundTestActivity)**
Simply wanted to make a sound when button is clicked. Didn't work for some reason. Has to be fixed.

**CardView Test 1(CardView1Activity)**
Wanted to see how cardviews work. Also utilised what I learned from the image rotation and made the card move according to finger movement.

**Viewpager2Test(FragmentTest, ViewpagerActivity, ViewPagerAdapter)**
I learned how to work with fragments and viewpager. User can swipe through five fragments.

**Send notification**
When the user clicks the button, it sends a custom made notification to the user. Just wanted to see how notifications work.

**Simple adjust test(AdjustTestActivity)**
Wanted that the buttons on the screen move up if keyboard pops up

**Receipt Saving app(CameraActivity, ReceiptAdapter, ReceiptData, ReceiptSavingActivity)**
User can take a photo and give it a name and a date. After giving the information the picture can be saved. It saves the urls, names and dates to realm database.
User can search images by typing to the search bar the name or date of the image. UI then shows the matches in a recyclerview adapter.


**CountDownTimer(CountDownTimerActivity)**
User can set a time in minutes and then program starts counting from that number. Feature to add hours or seconds should be added. 

**Million random strings(RandomStringsActivity)**
The goal was to create million random strings as fast as possible. This was the first time I used threading
and I used it so that the UI thread wouldn't jam because of the big work load.

**image gallery(FragmentForPics, ImageGalleryActivity, ImageGalleryAdapter, ImageGalleryData, ShowPicsActivity)**
User can set his own images to the app by setting a url and naming the picture. Both are saved to realm. 
Images are shown on a viewpager. The user can filter the images by the image type(jpg, png etc.).  
User should be able to remove images also but that feature doesn't work yet.

**City searching(CitySearchActivity)**
User can start writing any city name of the world. After a couple of letters the UI shows suggestions according to the letters. 
The city names comes from an api. This was the first time I worked with apis and retrieved data from them.

**Blackjack(Balance, BetMoney, BlackJackActivity, DealerHand, Hand, PlayerHand)**
It's a simplified version of the popular card came blackjack. The user plays against the "dealer",
whose game starts after the player has played his part. Player can place bets and he has a set amount of money in the start.
I should add a feature where the game stops when the player balance hits zero.

**"Sulkeiset"(Lamppu, LiikenneValot, SulkeisetActivity)**
My boss wanted us to understand properly how interfaces work in java. When the user taps the screen "traffic lights" will change color. 

