import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'app';

  constructor(private route: ActivatedRoute,
              private router: Router) {
    setInterval(() => {
      this.fix(window.location.href, window.location.search);
    }, 1000);


  }

  ngOnInit(): void {
  }

  fix(href: String, search: String): void {
    if (href.endsWith('#/callback') && search.includes('state') && search.includes('code')) {
      let code = search.split('&')[0].split('=')[1]
      let state = search.split('&')[1].split('=')[1]
      this.router.navigate(['/callback'], {queryParams: {'code': code, 'state': state}});
    } else if (!this.router.navigated) {
      this.router.initialNavigation()
    }
  }
}
