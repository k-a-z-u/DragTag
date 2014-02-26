DragTag
=======

A simple (java based) MP3 tagger using drag&amp;drop

# About the Project

I was quite unsatisfied by existing projects as they were slow in some places, or required a huge amount of clicks to tag, save and rename an audio file. Thus I started working on **DragTag** which is (currently) java based and operates via drag & drop. Files can dragged, edited, completed with data from external lookups (currently only **discogs**), saved and renamed/moved into your collections directory.

Nothing more, but nothing less ;)

# Dependencies

Currently the project depends on **json-simple** to parse the json API from **discogs**, and **JAudioTagger** write the new tags back to file. The latter one will, most likely, be replaced in the future as **KID3** gets write support.

For convenience, the **.jar** files for both dependencies are included.


# Building

Easiest way to build the project, is using **eclipse**. As it also depends on **KID3** (https://github.com/k-a-z-u/KID3), you have to clone both projects into your workspace:

```
cd /path/to/eclipse/workspace
git clone https://github.com/k-a-z-u/DragTag.git
git clone https://github.com/k-a-z-u/KID3.git
```

After cloning, just import both into your workspace (File -> Import -> Existing Projects into Workspace) and you should be ready to go.
