import React from 'react';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";


const rows = [
  {token: 1, name: 'Janek', memo: '', price: 100000},
  {token: 2, name: 'Eunho', memo: '', price: 50000},
  {token: 3, name: 'Kim', memo: '세종고', price: 30000},
  {token: 4, name: 'Moon', memo: '', price: 100000},
  {token: 5, name: 'Ronaldo', memo: '', price: 300000},
  {token: 6, name: 'Hwang', memo: '수서교회', price: 50000},
];

const GiftTable = () => {
  return (
    <Paper sx={{width: '70%', overflow: 'hidden'}}>
      <TableContainer sx={{maxHeight: 700}}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              <TableCell align='center' style={{width: '33%'}}>
                이름
              </TableCell>
              <TableCell align='center' style={{width: '33%'}}>
                비고
              </TableCell>
              <TableCell align='center' style={{width: '33%'}}>
                금액
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {rows
              .map((row) => {
                return (
                  <TableRow hover role="checkbox" tabIndex={-1} key={row.token}>
                    <TableCell align="center">{row.name}</TableCell>
                    <TableCell align="center">{row.memo}</TableCell>
                    <TableCell align="right" style={{paddingRight: '10%'}}>{row.price.toLocaleString()}</TableCell>
                  </TableRow>
                );
              })}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

export default GiftTable;