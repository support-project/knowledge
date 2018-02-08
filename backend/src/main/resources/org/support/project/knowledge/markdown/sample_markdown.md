# Markdown sample 
- This Markdown text is dispay on 'Display sample' area,
  You can check the display of Markdown

## Heading -> "#"
- "#" is heading
   - After the "#", by opening a space, write the title of the character
   - Change is the number the magnitude of the "#"

## Itemization -> "-"
- "-" is itemization
   - After the "#", by opening a space, write the contents of the bullet
   - If the space put three, and displayed by being indented as before
      - This will be displayed in a hierarchical structure as

## Itemization(with numbers) -> "1."
1. "1." is itemization(with numbers)
   1. After the "1.", by opening a space, write the contents of the bullet
   1.Except that the number is attached "-" and this is the same

## Strong -> "Two * (asterisk)"
- Emphasize that it surrounds the emphasis you want part in the statement in the "two * (asterisk)"
   - ** It is like this ** emphasized for example

## Border -> "Three or more of the * (asterisk)"
- If you want to put a border, it is described only "three or more of the * (asterisk)"
***
- Border is displayed above

## Link
- The part you want to put a link, to describe the [name of the link](link destination URL)
   - [Knowledge](https://information-knowledge.support-project.org)

## Code
- Where you want to display the code is enclosed in "` (back quote) "

```ruby
require 'redcarpet'
markdown = Redcarpet.new("Hello World!")
puts markdown.to_html
```

## Extended markdown syntax
- The part you want to put a web site introduction, to describe the [oembed WebSiteURL]
   - GitHub
      - [oembed https://github.com/support-project/knowledge]
   - SlideShare
      - [oembed http://www.slideshare.net/koda3/knowledge-information]
   - GoogleMap
      - [oembed https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d12966.919088372344!2d139.72177695!3d35.6590289!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x0%3A0x5bfe0248594cc802!2z5YWt5pys5pyo44OS44Or44K6!5e0!3m2!1sja!2sjp!4v1482300583922]


- Emoji
   - Pictographs are displayed by inserting a key between `:` and `:`
   - The list of pictographs is displayed on the link of `people nature objects places symbols`, so it is also possible to select from them
   - :+1: :smile:

- Link to other articles
   - If you enter the article ID after `#`, it will be a link to another article
   - If you enter `#`, the choices of linked articles are displayed
   - #1 ← Link to article "1"

- Formula in LaTeX format (block display)
   - If you enclose it with `$$`, it will display the formula as a block
   - $$ e^{i\theta} = \cos\theta + i\sin\theta $$
   
- Formula in LaTeX format (inline display)
   - Inline display when enclosed with `$`
   - The famous Euler formula is $e^{i\theta}=\cos\theta+i\sin\theta$ ．

- Formula in LaTeX format (code block)
   - In the code block \ `\` \ `math, write the mathematical expression directly (The above '$' is unnecessary)
   - `\\` is a newline

```math
Formula1: 

E = mc^2

\\

Formula2: 
\sum_{n=1}^\infty \frac{1}{n^2} = \frac{\pi^2}{6}

\\


The  famous  Euler  formula  is  $e^{i\theta}=\cos\theta+i\sin\theta$ ．


```


## Insert the image into the article
- Uploading an image allows you to press the "Show image" button, and pressing it will display the image
- Describe the display of images in the following format
    - `![Image name] (image URL)`
- ![Sample] (https://test-knowledge.support-project.org/open.account/icon/1)


## Insert the image into the article (upload the image copied to the clipboard directly)
- It is possible to paste directly into the text editing area


## Insert slide into article
- Uploading a slide (PDF) will allow you to press the "Slideshow" button and press it to display a slideshow


## More info
- [GitHub Flavored Markdown](https://help.github.com/articles/github-flavored-markdown/) 

