package com.tfr.scalaInAction.weKanban.view

/**
  * Created by Erik on 7/28/2017.
  */
object CreateStory {

  def apply(message:String = "") = {
    <html>
      <head>
        <title>Create new Story</title>
        <link rel="stylesheet" href="/css/main.css" type="text/css" media="screen" charset="utf-8"></link>
      </head>
      <body>
        <span class="message">{message}</span>
        <div class="createStory">
          <form action="/card/save" method="post" accept-charset="utf-8">
            <fieldset>
              <legend>Create a new Story</legend>
              <div class="section">
                <label for="storyNumber">Story Number<span class="subtle">(uniquely identifies a story)</span></label>
                <input type="text" size="10" maxLength="10" minLength="3" name="storyNumber" id="storyNumber" />
              </div>
              <div class="section">
                <label for="title">Title<span class="subtle">(describe the story)</span></label>
                <textarea rows="5" cols="30" name="title" id="title"></textarea>
              </div>
              <div class="section">
                <button type="submit">Save</button>
              </div>
            </fieldset>
          </form>
          <span class="linkLabel">Go to Kanban Board</span>
        </div>
      </body>
    </html>
  }

}
