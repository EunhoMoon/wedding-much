import React from 'react';
import {Paper, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";
import GiftTableRow from "./GiftTableRow";

const GiftTable = props => {
  const {gifts, total, sort, onDelete, onUpdate, onSorting} = props;

  const changeSortingHandler = (e) => {
    const by = e.target.id.toUpperCase();
    const direction = sort.direction === 'ASC' ? 'DESC' : 'ASC';

    onSorting(by, direction);
  }

  return (
    <Paper sx={{width: '100%', overflow: 'hidden'}}>
      <TableContainer sx={{maxHeight: 700}}>
        <Table stickyHeader aria-label="sticky table">
          <TableHead>
            <TableRow>
              <TableCell align="center" id="id" onClick={changeSortingHandler} style={{width: '10%'}}>#</TableCell>
              <TableCell align='center' id="name" onClick={changeSortingHandler} style={{width: '25%'}}>
                이름
              </TableCell>
              <TableCell align='center' style={{width: '30%'}}>
                비고
              </TableCell>
              <TableCell align='center' id="price" onClick={changeSortingHandler} style={{width: '15%'}}>
                금액
              </TableCell>
              <TableCell align='center' style={{width: '10%'}}>
                수정
              </TableCell>
              <TableCell align='center' style={{width: '10%'}}>
                삭제
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {gifts && gifts.length > 0 && gifts.map((gift, _idx) => {
              return <GiftTableRow key={_idx} num={total - _idx} gift={gift} onDelete={onDelete} onUpdate={onUpdate}/>
            })}
            {gifts.length <= 0 && <TableRow><TableCell colSpan={5} align="center">데이터를 입력하세요.</TableCell></TableRow>}
          </TableBody>
        </Table>
      </TableContainer>
    </Paper>
  );
};

export default GiftTable;