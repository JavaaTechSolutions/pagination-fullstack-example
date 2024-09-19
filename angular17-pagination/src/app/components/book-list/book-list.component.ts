import { Component, OnInit } from '@angular/core';
import { Book } from '../../model/book.model';
import { BookService } from '../../services/book.service';
import {FormsModule} from '@angular/forms'
import { NgxPaginationModule } from 'ngx-pagination';

@Component({
  selector: 'app-book-list',
  standalone: true,
  imports: [FormsModule, NgxPaginationModule],
  templateUrl: './book-list.component.html',
  styleUrl: './book-list.component.css'
})
export class BookListComponent implements OnInit {

  books: Book[] = [];
  currentBooks: Book = {};

  currentIndex = -1;
  title = '';

  page = 1;
  count = 0;
  pageSize = 5;
  pageSizes = [5, 10, 15];

  constructor(private bookService: BookService) {}
 
  ngOnInit(): void {
    this.retrieveBooks();
  }

  retrieveBooks(): void {
    const params = this.getRequestParams(this.title, this.page, this.pageSize);

    this.bookService.getAll(params)
      .subscribe({
        next: (data) => {
          const {books, totalItems} = data;
          this.books = books;
          this.count = totalItems;
        },
        error: (err) => {
          console.log(err);
        }
      })
  }

  getRequestParams(searchTitle: string, page: number, pageSize: number): any {
    let params: any = {};

    if (searchTitle) {
      params[`title`] = searchTitle;
    }

    if (page) {
      params[`page`] = page - 1;
    }

    if (pageSize) {
      params[`size`] = pageSize;
    }

    return params;
  }

  searchTitle(): void {
    this.page = 1;
    this.retrieveBooks();
  }

  handlePageChange(event: number): void {
    this.page = event;
    this.retrieveBooks();
  }

  handlePageSizeChange(event: any): void {
    this.pageSize = event.target.value;
    this.page = 1;
    this.retrieveBooks();
  }

}
