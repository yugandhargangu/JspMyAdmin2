export class Databases {
    database_list: Database[];
    footer_info: Database;
    count: number;
}

export class Database {
    database: string;
    collation: string;
    tables: number;
    rows: number;
    data: number;
    indexes: number;
    total: number;
}
