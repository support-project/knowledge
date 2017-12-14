
### Easy Wizard
Tool to easily create a wizard.

## Example

```html


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
    <link rel="stylesheet" href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap-theme.min.css">
    <script src="//code.jquery.com/jquery-2.1.4.js"></script>
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="easyWizard.css">
</head>
<body>
    <div class="text-center">
        <button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
            Wizard
        </button>
    </div>
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="myModalLabel">Easy Wizard</h4>
                </div>
                <div class="modal-body wizard-content">
                    <div class="wizard-step">
                        Step 1 <br>
                        Adipisicing aut repellat maiores hic ipsum. Adipisci quod minus non architecto maxime maxime autem inventore sunt autem. Sint sit vero soluta recusandae fuga est quae. In aliquid rerum aliquam sint!
                    </div>
                    <div class="wizard-step">
                        Step 2 <br>
                        Adipisicing aut repellat maiores hic ipsum. Adipisci quod minus non architecto maxime maxime autem inventore sunt autem. Sint sit vero soluta recusandae fuga est quae. In aliquid rerum aliquam sint!
                    </div>
                </div>
                <div class="modal-footer wizard-buttons">
                    <!-- The wizard button will be inserted here. -->
                </div>
            </div>
        </div>
    </div>
    <script src="easyWizard.js"></script> 
    <script>
        $(document).on("ready", function(){
            $("#myModal").wizard({
                onfinish:function(){
                    console.log("Hola mundo");
                }
            });
        });
    </script>
</body>


```



