<nav class="light-blue lighten-1" role="navigation">
    <div class="nav-wrapper container">
        <div class="add-imagem">
            <form action="profile" method="POST" enctype="multipart/form-data">
                <input class="btn-flat white-text" type="file" name="imagem" accept="image/*" required/>
                <button type="submit" class="search-btn btn-flat"><i class="material-icons white-text">add</i></button></input>
            </form>
        </div>

        <ul class="right hide-on-med-and-down">
            <li>
                <form action="/search" method="GET"> 
                    <div class="row">
                        <div class="col search"><input class="white-text search center" type="text" name="q" required /></div>
                        <div class="col search"><button class="btn-flat white-text search-btn"><i class="material-icons search">search</i></button></div>
                    </div>
                </form>
            </li>
            <li><a href="/feed"><i class="material-icons search">public</i></a></li>
            <li><a href="/profile"><i class="material-icons search">account_circle</i></a></li>
            <li><a href="/logout"><i class="material-icons search">power_settings_new</i></a></li>
        </ul>

        <ul id="nav-mobile" class="sidenav">
            <li><a href="/feed">Feed</a></li>
            <li><a href="/profile">My Profile</a></li>
            <li><a href="/logout">Logout</a></li>
        </ul>
        
        <a href="#" data-target="nav-mobile" class="sidenav-trigger"><i class="material-icons">menu</i></a>
    </div> 
</nav>